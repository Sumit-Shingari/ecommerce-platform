import React, { useEffect, useState } from "react";
import "../styles/cart.css";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
import { toast } from 'react-hot-toast';
import api from "../api/axiosConfig";

const CartPage = () => {

  const { currentUser, fetchCartCount } = useAuth();
  const navigate = useNavigate();

  const [cart, setCart] = useState({
    items: [],
    subtotal: 0
  });

  const checkout = async () => {

    try {

      const token = await currentUser.getIdToken();

      const res = await api.post(
        "/api/orders/checkout",
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      alert("Order placed successfully!");

      // 🔥 refresh cart
      await fetchCart();

      // 🔥 refresh navbar count
      await fetchCartCount(currentUser);

      navigate("/orders");

    } catch (error) {

      if (error.response) {

        toast.error(
          error.response.data?.message ||
          "Checkout failed"
        );

      } else {

        toast.error("Server error");

      }
    }
  };

  const fetchCart = async () => {

    const token = await currentUser.getIdToken();

    const res = await api.get(
      "/api/cart",
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );

    setCart(res.data);
  };

  useEffect(() => {

    if (currentUser) {
      fetchCart();
    }

  }, [currentUser]);

  const updateQuantity = async (itemId, qty) => {

    if (qty < 1) return;

    const token = await currentUser.getIdToken();

    await api.put(
      "/api/cart/update",
      {
        itemId: itemId,
        quantity: qty
      },
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );

    fetchCart();
    fetchCartCount(currentUser);
  };

  const removeItem = async (itemId) => {

    const token = await currentUser.getIdToken();

    await api.delete(
      `/api/cart/${itemId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );

    fetchCart();
    fetchCartCount(currentUser);
  };

  return (
    <div className="cart-container">

      <h2>Your Cart</h2>

      {cart.items.length === 0 && (
        <p>Your cart is empty</p>
      )}

      {cart.items.map(item => (

        <div key={item.itemId} className="cart-item">

          <img src={item.imageUrl} alt={item.productName} />

          <div className="cart-info">

            <h4>{item.brand}</h4>
            <p>{item.productName}</p>

            <p>
              Size: {item.size} | Color: {item.color}
            </p>

            <div className="cart-qty">

              <button
                onClick={() => updateQuantity(item.itemId, item.quantity - 1)}
              >
                -
              </button>

              <span>{item.quantity}</span>

              <button
                onClick={() => updateQuantity(item.itemId, item.quantity + 1)}
              >
                +
              </button>

            </div>

            <button
              className="remove-btn"
              onClick={() => removeItem(item.itemId)}
            >
              Remove
            </button>

          </div>

          <div className="cart-price">

            ₹{item.price * item.quantity}

          </div>

        </div>

      ))}

      <div className="cart-summary">

        <h3>Subtotal</h3>

        <h2>₹{cart.subtotal}</h2>

        <button
          className="checkout-btn"
          onClick={checkout}
        >
          Proceed to Checkout
        </button>

      </div>

    </div>
  );
};

export default CartPage;