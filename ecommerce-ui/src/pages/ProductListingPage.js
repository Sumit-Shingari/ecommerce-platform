import React, { useEffect, useState } from "react";
import "../styles/plp.css";
import { FaHeart } from "react-icons/fa";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
import AuthModal from "../components/AuthModal";
import api from "../api/axiosConfig";

import { useSearchParams, useLocation } from "react-router-dom";

const ProductListingPage = () => {

    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const [showWishlistPopup, setShowWishlistPopup] = useState(false);
    const [showAuthModal, setShowAuthModal] = useState(false);
    const [authMode, setAuthMode] = useState("login");
    const queryParam = searchParams.get("query") || "";

    const [products, setProducts] = useState([]);

    const { currentUser, wishlistIds, setWishlistIds } = useAuth();

    const [facets, setFacets] = useState({
        brandFacets: {},
        sizeFacets: {},
        colorFacets: {}
    });

    const [filters, setFilters] = useState({
        brands: [],
        sizes: [],
        colors: []
    });

    const { loadWishlist } = useAuth();

    const handleWishlistToggle = async (productId) => {

        if (!currentUser) {

            setShowWishlistPopup(true);
            return;
        }

        const token = await currentUser.getIdToken();

        await api.post(
            `/api/wishlist/${productId}`,
            {},
            {
                headers: { Authorization: `Bearer ${token}` }
            }
        );

        // 🔥 refresh wishlist from backend
        await loadWishlist(currentUser);
    };

    // 🔥 FETCH PRODUCTS
    const fetchProducts = async () => {
        try {
            console.log("Sending query:", queryParam);

            const res = await api.post(
                "/api/products/search",
                {
                    query: queryParam, // ✅ FIXED
                    brands: filters.brands,
                    sizes: filters.sizes,
                    colors: filters.colors,
                    priceFrom: null,
                    priceTo: null,
                    sort: null,
                    page: 0,
                    sizePerPage: 20
                }
            );

            setProducts(res.data.products);
            setFacets({
                brandFacets: res.data.brandFacets,
                sizeFacets: res.data.sizeFacets,
                colorFacets: res.data.colorFacets
            });

        } catch (error) {
            console.error("Search error:", error);
        }
    };

    // 🔥 REFETCH WHEN QUERY OR FILTERS CHANGE
    useEffect(() => {
        fetchProducts();
    }, [filters, queryParam]);

    // 🔥 RESET FILTERS WHEN NEW SEARCH QUERY
    useEffect(() => {
        setFilters({
            brands: [],
            sizes: [],
            colors: []
        });
    }, [queryParam]);

    const toggleFilter = (type, value) => {
        setFilters(prev => {
            const exists = prev[type].includes(value);

            return {
                ...prev,
                [type]: exists
                    ? prev[type].filter(v => v !== value)
                    : [...prev[type], value]
            };
        });
    };


    return (
        <>
            <div className="plp-container">

                {/* LEFT SIDEBAR */}
                <div className="plp-sidebar">

                    <h3>Filters</h3>

                    {/* BRAND */}
                    <div className="facet-section">
                        <h4>Brand</h4>

                        <div className="facet-list">
                            {Object.entries(facets.brandFacets).map(([brand, count]) => (
                                <label key={brand} className="facet-checkbox">
                                    <input
                                        type="checkbox"
                                        checked={filters.brands.includes(brand)}
                                        onChange={() => toggleFilter("brands", brand)}
                                    />
                                    <span className="facet-label">{brand}</span>
                                    <span className="facet-count">({count})</span>
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* SIZE */}
                    <div className="facet-section">
                        <h4>Size</h4>

                        <div className="facet-list">
                            {Object.entries(facets.sizeFacets).map(([size, count]) => (
                                <label key={size} className="facet-checkbox">
                                    <input
                                        type="checkbox"
                                        checked={filters.sizes.includes(size)}
                                        onChange={() => toggleFilter("sizes", size)}
                                    />
                                    <span className="facet-label">{size}</span>
                                    <span className="facet-count">({count})</span>
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* COLOR */}
                    <div className="facet-section">
                        <h4>Color</h4>

                        <div className="facet-list">
                            {Object.entries(facets.colorFacets).map(([color, count]) => (
                                <label key={color} className="facet-checkbox">
                                    <input
                                        type="checkbox"
                                        checked={filters.colors.includes(color)}
                                        onChange={() => toggleFilter("colors", color)}
                                    />
                                    <span className="facet-label">{color}</span>
                                    <span className="facet-count">({count})</span>
                                </label>
                            ))}
                        </div>
                    </div>

                </div>

                {/* RIGHT CONTENT */}
                <div className="plp-content">

                    {/* APPLIED FILTER CHIPS */}
                    <div className="applied-filters">

                        {filters.brands.map(brand => (
                            <div
                                key={brand}
                                className="filter-chip"
                                onClick={() => toggleFilter("brands", brand)}
                            >
                                {brand} <span>✕</span>
                            </div>
                        ))}

                        {filters.sizes.map(size => (
                            <div
                                key={size}
                                className="filter-chip"
                                onClick={() => toggleFilter("sizes", size)}
                            >
                                {size} <span>✕</span>
                            </div>
                        ))}

                        {filters.colors.map(color => (
                            <div
                                key={color}
                                className="filter-chip"
                                onClick={() => toggleFilter("colors", color)}
                            >
                                {color} <span>✕</span>
                            </div>
                        ))}

                        {(filters.brands.length ||
                            filters.sizes.length ||
                            filters.colors.length) > 0 && (
                                <div
                                    className="clear-all"
                                    onClick={() =>
                                        setFilters({
                                            brands: [],
                                            sizes: [],
                                            colors: []
                                        })
                                    }
                                >
                                    Clear All
                                </div>
                            )}

                    </div>

                    {/* PRODUCT GRID */}
                    <div className="product-grid">
                        {products.map(product => (
                            <div key={product.id} className="product-card"
                            onClick={()=>navigate(`/product/${product.id}`)}
                            >

                                <div className="wishlist-btn"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        handleWishlistToggle(product.id);
                                    }}>
                                    <FaHeart
                                        className={wishlistIds.includes(Number(product.id)) ? "active" : ""}
                                    />
                                </div>

                                <img src={product.thumbnailUrl} alt={product.name} />
                                <h4>{product.brand}</h4>
                                <p>{product.name}</p>
                                <div className="price">₹{product.minPrice}</div>

                            </div>
                        ))}
                    </div>

                </div>

            </div>

            {showWishlistPopup && (
                <div className="wishlist-login-popup">

                    <div className="wishlist-popup-content">

                        <h3>Save to Wishlist</h3>

                        <p>Please login or create an account to save products.</p>

                        <div className="wishlist-popup-actions">

                            <button
                                className="btn-outline"
                                onClick={() => {
                                    setAuthMode("login");
                                    setShowAuthModal(true);
                                    setShowWishlistPopup(false);
                                }}
                            >
                                Login
                            </button>

                            <button
                                className="btn-primary"
                                onClick={() => {
                                    setAuthMode("signup");
                                    setShowAuthModal(true);
                                    setShowWishlistPopup(false);
                                }}
                            >
                                Sign Up
                            </button>

                        </div>

                        <span
                            className="popup-close"
                            onClick={() => setShowWishlistPopup(false)}
                        >
                            ✕
                        </span>

                    </div>

                </div>
            )}

            {
                showAuthModal && (
                    <AuthModal
                        mode={authMode}
                        closeModal={() => setShowAuthModal(false)}
                    />
                )
            }

        </>

    );
};

export default ProductListingPage;