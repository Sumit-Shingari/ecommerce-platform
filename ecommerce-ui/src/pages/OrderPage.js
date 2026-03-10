import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../auth/AuthContext";
import "../styles/orders.css";

const OrdersPage = () => {

    const { currentUser } = useAuth();

    const [orders, setOrders] = useState([]);

    const fetchOrders = async () => {

        const token = await currentUser.getIdToken();

        const res = await axios.get(
            "http://localhost:8080/api/orders",
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );

        setOrders(res.data);
    };

    useEffect(() => {
        fetchOrders();
    }, []);

    return (
  <div className="orders-page">

    <h2 className="orders-title">Your Orders</h2>

    {orders.map(order => (
      <div key={order.orderId} className="order-card">

        <div className="order-header">
          <div>
            <strong>Order #{order.orderId}</strong>
          </div>

          <div className="order-status">
            {order.status}
          </div>
        </div>

        {order.items.map(item => (
          <div key={item.skuId} className="order-item">

            <div className="order-image">
              <img src={item.imageUrl || "https://dummyimage.com/80x100"} />
            </div>

            <div className="order-info">

              <div className="product-name">
                {item.productName}
              </div>

              <div className="product-brand">
                {item.brand}
              </div>

              <div className="product-meta">
                Size {item.size} | Qty {item.quantity}
              </div>

            </div>

          </div>
        ))}

        <div className="order-total">
          Total ₹{order.totalAmount}
        </div>

      </div>
    ))}

  </div>
);

};

export default OrdersPage;