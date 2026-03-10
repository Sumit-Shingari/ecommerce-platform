import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../auth/AuthContext";

const WishlistPage = () => {

  const { currentUser } = useAuth();
  const [products, setProducts] = useState([]);

  useEffect(() => {
    if (!currentUser) return;

    const fetchWishlist = async () => {
      const token = await currentUser.getIdToken();

      const res = await axios.get(
        "http://localhost:8080/api/wishlist",
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );

      setProducts(res.data.products);
    };

    fetchWishlist();

  }, [currentUser]);

  return (
    <div className="wishlist-page">
      <h2>My Wishlist</h2>

      {products.length === 0 ? (
        <p>Your wishlist is empty.</p>
      ) : (
        <div className="product-grid">
          {products.map(product => (
            <div key={product.id} className="product-card">
              <img src={product.imageUrl} alt={product.name} />
              <h4>{product.brand}</h4>
              <p>{product.name}</p>
              <div>₹{product.price}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default WishlistPage;