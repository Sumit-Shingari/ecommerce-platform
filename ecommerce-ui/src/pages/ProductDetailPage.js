import React, { useEffect, useState } from "react";
import "../styles/pdp.css";
import { useAuth, } from "../auth/AuthContext";
import { useParams } from "react-router-dom";
import api from "../api/axiosConfig";

const ProductDetailPage = () => {

    const { id } = useParams();

    const [product, setProduct] = useState(null);

    const [selectedSize, setSelectedSize] = useState(null);
    const [selectedColor, setSelectedColor] = useState(null);
    const [selectedSku, setSelectedSku] = useState(null);
    const { currentUser } = useAuth();
    const { setCartCount } = useAuth();
    const { fetchCartCount } = useAuth();

    const addToCart = async () => {

        if (!selectedSize) {
            alert("Please select a size");
            return;
        }

        if (!selectedColor) {
            alert("Please select a color");
            return;
        }

        const sku = product.skus.find(
            s => s.size === selectedSize && s.color === selectedColor
        );

        if (!sku) {
            alert("This combination is not available");
            return;
        }

        if (sku.stock <= 0) {
            alert("This product is out of stock");
            return;
        }


        if (!currentUser) {
            alert("Please login to add items to cart");
            return;
        }

        try {

            const token = await currentUser.getIdToken();

            await api.post(
                "/api/cart/add",
                {
                    skuId: selectedSku.skuId,
                    quantity: 1
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            // 🔥 Refresh cart count from backend
            await fetchCartCount(currentUser);

            alert("Added to cart");

        } catch (error) {

            console.error("Add to cart error:", error);

        }
    };

    useEffect(() => {

        if (selectedSize && selectedColor) {

            const sku = product.skus.find(
                s => s.size === selectedSize && s.color === selectedColor
            );

            setSelectedSku(sku);

        }

    }, [selectedSize, selectedColor]);

    useEffect(() => {

        const fetchProduct = async () => {

            const res = await api.get(
                `/api/products/${id}`
            );

            setProduct(res.data);

        };

        fetchProduct();

    }, [id]);

    if (!product) return <div>Loading...</div>;

    const sizes = [...new Set(product.skus.map(s => s.size))];
    const colors = [...new Set(product.skus.map(s => s.color))];

    return (
        <div className="pdp-container">

            <div className="pdp-left">

                <img
                    src={product.skus[0].imageUrl}
                    alt={product.name}
                />

            </div>

            <div className="pdp-right">

                <h2>{product.brand}</h2>

                <h3>{product.name}</h3>

                <div className="pdp-price">
                    ₹{product.skus[0].price}
                </div>

                <div className="pdp-section">

                    <h4>Select Size</h4>

                    <div className="size-options">

                        {sizes.map(size => (
                            <button
                                key={size}
                                className={selectedSize === size ? "active" : ""}
                                onClick={() => setSelectedSize(size)}
                            >
                                {size}
                            </button>
                        ))}

                    </div>

                </div>

                <div className="pdp-section">

                    <h4>Select Color</h4>

                    <div className="color-options">

                        {colors.map(color => (
                            <button
                                key={color}
                                className={selectedColor === color ? "active" : ""}
                                onClick={() => setSelectedColor(color)}
                            >
                                {color}
                            </button>
                        ))}

                    </div>

                </div>

                <button
                    className="add-cart-btn"
                    onClick={addToCart}
                >
                    Add To Cart
                </button>

            </div>

        </div>
    );
};

export default ProductDetailPage;