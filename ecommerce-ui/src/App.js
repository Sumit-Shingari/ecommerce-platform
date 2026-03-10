import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./auth/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import Dashboard from "./pages/Dashboard";
import AdminPanel from "./pages/AdminPanel";
import Navbar from "./components/Navbar";
import "./styles/global.css";
import ProductListingPage from "./pages/ProductListingPage";
import Home from "./pages/Home";
import WishlistPage from "./pages/WishlistPage";
import { Toaster } from "react-hot-toast";
import ProductDetailPage from "./pages/ProductDetailPage";
import CartPage from "./pages/CartPage";
import OrdersPage from "./pages/OrderPage";

function App() {
  return (
    <Router>
      <AuthProvider>
        <Navbar />
        <Toaster position="top-center" />
        <Routes>
          <Route path="/wishlist" element={<WishlistPage />} />
          <Route path="/" element={<Home />} />
          <Route path="/product/:id" element={<ProductDetailPage />} />

          <Route
            path="/orders"
            element={<OrdersPage />}
          />

          <Route
            path="/cart"
            element={<CartPage />}
          />
          <Route path="/dashboard"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          <Route path="/products" element={<ProductListingPage />} />

          <Route
            path="/admin"
            element={
              <ProtectedRoute role="ROLE_ADMIN">
                <AdminPanel />
              </ProtectedRoute>
            }
          />
        </Routes>
      </AuthProvider>
    </Router>
  );
}

export default App;