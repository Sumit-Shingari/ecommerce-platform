import React, { useState, useEffect, useRef } from "react";
import { fetchSuggestions } from "../api/searchService";
import { FaShoppingBag, FaSearch, FaHeart } from "react-icons/fa";
import { useAuth } from "../auth/AuthContext";
import AuthModal from "./AuthModal";
import "../styles/navbar.css";
import { useNavigate } from "react-router-dom";


const Navbar = () => {
  const { currentUser, logout, wishlistIds, cartCount } = useAuth();
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [mode, setMode] = useState("login");
  const [activeMenu, setActiveMenu] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [suggestions, setSuggestions] = useState([]);
  const [activeIndex, setActiveIndex] = useState(-1);
  const [showWishlist, setShowWishlist] = useState(false);

  const wishlistCount = wishlistIds?.length || 0;

  const debounceRef = useRef(null);

  const redirectToSearch = (keyword) => {
    if (!keyword) return;

    navigate(`/products?query=${encodeURIComponent(keyword)}`);

    setSearchText(keyword);
    setShowSuggestions(false);
  };

  const openLogin = () => {
    setMode("login");
    setShowModal(true);
  };

  const openSignup = () => {
    setMode("signup");
    setShowModal(true);
  };

  const handleLogout = async () => {
    await logout();
  };

  useEffect(() => {
    if (!searchText || searchText.length < 2) {
      setSuggestions([]);
      return;
    }

    clearTimeout(debounceRef.current);

    debounceRef.current = setTimeout(async () => {
      try {
        const result = await fetchSuggestions(searchText);
        setSuggestions(result);
        setShowSuggestions(true);
      } catch (err) {
        console.error("Suggest error:", err);
      }
    }, 300);

  }, [searchText]);

  return (
    <>
      <header className="fashion-header">
        <div className="fashion-top">

          {/* LEFT */}
          <div className="left-section">
            <div
              className="fashion-logo"
              onClick={() => navigate("/")}
            >
              StyleSphere1
            </div>

            <nav className="fashion-menu">
              {["Men", "Women", "Kids", "Beauty", "Studio"].map((item) => (
                <div
                  key={item}
                  className="menu-item"
                  onMouseEnter={() => setActiveMenu(item)}
                  onMouseLeave={() => setActiveMenu(null)}
                >
                  {/* 🔥 MAIN CATEGORY CLICK */}
                  <span
                    onClick={() => {
                      redirectToSearch(item.toLowerCase());
                      setActiveMenu(null);
                    }}
                    style={{ cursor: "pointer" }}
                  >
                    {item}
                  </span>

                  {activeMenu === item && (
                    <div className="mega-menu">

                      {/* COLUMN 1 */}
                      <div className="mega-col">
                        <h4>Topwear</h4>
                        <span onClick={() => { redirectToSearch("tshirt"); setActiveMenu(null); }}>
                          T-Shirts
                        </span>
                        <span onClick={() => { redirectToSearch("shirt"); setActiveMenu(null); }}>
                          Shirts
                        </span>
                        <span onClick={() => { redirectToSearch("jacket"); setActiveMenu(null); }}>
                          Jackets
                        </span>
                      </div>

                      {/* COLUMN 2 */}
                      <div className="mega-col">
                        <h4>Bottomwear</h4>
                        <span onClick={() => { redirectToSearch("jeans"); setActiveMenu(null); }}>
                          Jeans
                        </span>
                        <span onClick={() => { redirectToSearch("trousers"); setActiveMenu(null); }}>
                          Trousers
                        </span>
                        <span onClick={() => { redirectToSearch("shorts"); setActiveMenu(null); }}>
                          Shorts
                        </span>
                      </div>

                      {/* COLUMN 3 */}
                      <div className="mega-col">
                        <h4>Footwear</h4>
                        <span onClick={() => { redirectToSearch("sneakers"); setActiveMenu(null); }}>
                          Sneakers
                        </span>
                        <span onClick={() => { redirectToSearch("casual shoes"); setActiveMenu(null); }}>
                          Casual Shoes
                        </span>
                        <span onClick={() => { redirectToSearch("sandals"); setActiveMenu(null); }}>
                          Sandals
                        </span>
                      </div>

                      {/* IMAGE COLUMN */}
                      <div
                        className="mega-image"
                        onClick={() => {
                          redirectToSearch("trending");
                          setActiveMenu(null);
                        }}
                      >
                        <img
                          src="https://images.unsplash.com/photo-1520975922284-9d1a09b84cfa?q=80&w=600"
                          alt="Fashion"
                        />
                        <p>Trending Styles</p>
                      </div>

                    </div>
                  )}
                </div>
              ))}
            </nav>
          </div>

          {/* CENTER SEARCH */}
          <div className="fashion-search-wrapper">

            <div className="fashion-search">
              <FaSearch className="search-icon" />

              <input
                type="text"
                placeholder="Search for products, brands and more"
                value={searchText}
                onChange={(e) => {
                  setSearchText(e.target.value);
                  setActiveIndex(-1);
                  if (e.target.value.length >= 2) {
                    setShowSuggestions(true);
                  } else {
                    setShowSuggestions(false);
                  }
                }}
                onFocus={() => {
                  if (searchText.length >= 2) {
                    setShowSuggestions(true);
                  }
                }}
                onBlur={() => {
                  setTimeout(() => setShowSuggestions(false), 150);
                }}
                onKeyDown={(e) => {

                  // ↓ Arrow Down
                  if (e.key === "ArrowDown") {
                    e.preventDefault();
                    setActiveIndex(prev =>
                      prev < suggestions.length - 1 ? prev + 1 : prev
                    );
                  }

                  // ↑ Arrow Up
                  if (e.key === "ArrowUp") {
                    e.preventDefault();
                    setActiveIndex(prev => (prev > 0 ? prev - 1 : 0));
                  }

                  // ENTER
                  if (e.key === "Enter") {
                    e.preventDefault();

                    const selectedKeyword =
                      activeIndex >= 0
                        ? suggestions[activeIndex]
                        : searchText;

                    if (!selectedKeyword) return;

                    setShowSuggestions(false);
                    navigate(
                      `/products?query=${encodeURIComponent(selectedKeyword)}`
                    );
                  }
                }}
              />
            </div>

            {showSuggestions && suggestions.length > 0 && (
              <div className="search-suggestions">
                {suggestions.map((item, index) => (
                  <div
                    key={index}
                    className={`suggestion-item ${index === activeIndex ? "active" : ""
                      }`}
                    onMouseDown={() => {
                      setShowSuggestions(false);
                      navigate(
                        `/products?query=${encodeURIComponent(item)}`
                      );
                    }}
                  >
                    {item}
                  </div>
                ))}
              </div>
            )}

          </div>

          {/* RIGHT SIDE */}
          <div className="fashion-right">

            {!currentUser ? (
              <>
                <button className="btn-outline" onClick={openLogin}>
                  Login
                </button>

                <button className="btn-primary" onClick={openSignup}>
                  Sign Up
                </button>
              </>
            ) : (
              <div className="profile-wrapper">
                <div className="profile-trigger">
                  Hi, {currentUser.email} ▾
                </div>

                {/* 🔥 PROFILE DROPDOWN */}
                <div className="profile-dropdown">
                  <span onClick={() => navigate("/orders")}>Orders</span>
                  <span onClick={() => navigate("/wishlist")}>Wishlist</span>
                  <span onClick={handleLogout}>Logout</span>
                </div>
              </div>
            )}

            {/* ❤️ WISHLIST ICON */}

            <div className="wishlist-wrapper">

              <div
                className="wishlist-icon"
                onMouseEnter={() => setShowWishlist(true)}
              >
                <FaHeart size={18} />

                {wishlistCount > 0 && (
                  <span className="wishlist-badge">
                    {wishlistCount}
                  </span>
                )}
              </div>

              {showWishlist && (
                <div
                  className="wishlist-hover"
                  onMouseLeave={() => setShowWishlist(false)}
                >

                  {currentUser ? (
                    <>
                      <p>
                        <strong>{wishlistCount}</strong>{" "}
                        {wishlistCount === 1 ? "item" : "items"} in Wishlist
                      </p>

                      <button
                        className="wishlist-btn-link"
                        onClick={() => navigate("/wishlist")}
                      >
                        View Wishlist →
                      </button>
                    </>
                  ) : (
                    <>
                      <p><strong>Login / Sign up</strong> to use Wishlist</p>

                      <button
                        className="wishlist-btn-link"
                        onClick={openLogin}
                      >
                        Sign In →
                      </button>
                      <button
                        className="wishlist-btn-link"
                        onClick={openSignup}
                      >
                        Sign up →
                      </button>
                    </>
                  )}

                </div>
              )}

            </div>

            {/* CART ICON */}
            <div
              className="cart-icon"
              onClick={() => navigate("/cart")}
            >

              <FaShoppingBag size={20} />

              {cartCount > 0 && (
                <span className="cart-badge">
                  {cartCount}
                </span>
              )}

            </div>

          </div>
        </div>
      </header>

      {showModal && (
        <AuthModal
          mode={mode}
          closeModal={() => setShowModal(false)}
        />
      )}
    </>
  );
};

export default Navbar;