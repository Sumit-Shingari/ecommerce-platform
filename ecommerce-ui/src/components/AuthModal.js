import React, { useState } from "react";
import axios from "axios";
import { auth } from "../auth/firebase";
import {
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword
} from "firebase/auth";

import "../styles/auth.css";

const AuthModal = ({ mode, closeModal }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      let userCredential;

      if (mode === "login") {
        userCredential = await signInWithEmailAndPassword(auth, email, password);
      } else {
        userCredential = await createUserWithEmailAndPassword(auth, email, password);
      }

      const token = await userCredential.user.getIdToken();

      await axios.post(
        "http://localhost:8080/api/auth/sync",
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      closeModal();

    } catch (error) {
      alert(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-card">

        {/* CLOSE BUTTON */}
        <button className="close-btn" onClick={closeModal}>
          ✕
        </button>

        <h2>{mode === "login" ? "Welcome Back" : "Create Account"}</h2>

        <form onSubmit={handleSubmit}>
          <input
            type="email"
            placeholder="Email Address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit" disabled={loading}>
            {loading
              ? "Please wait..."
              : mode === "login"
              ? "Login"
              : "Sign Up"}
          </button>
        </form>

      </div>
    </div>
  );
};

export default AuthModal;