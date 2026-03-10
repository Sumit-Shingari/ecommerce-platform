import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/home.css";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className="home-container">

      <div className="hero-banner">
        <img
          src="https://images.unsplash.com/photo-1490481651871-ab68de25d43d?w=1400"
          alt="Fashion"
        />
        <div className="hero-text">
          <h1>Discover Your Style</h1>
          <button onClick={() => navigate("/products")}>
            Shop Now
          </button>
        </div>
      </div>

    </div>
  );
};

export default Home;