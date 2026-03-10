import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

function ProtectedRoute({ children, role }) {
  const { currentUser } = useAuth();

  if (!currentUser) {
    return <Navigate to="/login" />;
  }

  if (role) {
    const userRole = JSON.parse(atob(localStorage.getItem("token").split(".")[1])).role;
    if (userRole !== role) {
      return <Navigate to="/dashboard" />;
    }
  }

  return children;
}

export default ProtectedRoute;