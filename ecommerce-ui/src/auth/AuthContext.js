import React, { createContext, useContext, useEffect, useState } from "react";
import { auth } from "./firebase";
import { onAuthStateChanged, signOut } from "firebase/auth";
import  api  from "../api/axiosConfig";


const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {

  const [currentUser, setCurrentUser] = useState(null);
  const [wishlistIds, setWishlistIds] = useState([]);
  const [cartCount, setCartCount] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetchCartCount = async (user) => {

    try {

      const token = await user.getIdToken();

      const res = await api.get(
        "/api/cart/count",
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      setCartCount(res.data);

    } catch (err) {
      console.error("Cart count error", err);
    }
  };




  /**
   * 🔥 LOAD WISHLIST FROM BACKEND
   */
  const loadWishlist = async (user) => {

    if (!user) {
      setWishlistIds([]);
      return;
    }

    try {

      const token = await user.getIdToken();

      const res = await api.get(
        "/api/wishlist",
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      const ids = res.data.products.map(p => Number(p.id));

      setWishlistIds(ids);

    } catch (err) {
      console.error("Wishlist fetch error:", err);
    }
  };

  /**
   * 🔥 AUTH STATE LISTENER
   */
  useEffect(() => {

    const unsubscribe = onAuthStateChanged(auth, async (user) => {

      if (user) {

        const token = await user.getIdToken();

        localStorage.setItem("token", token);

        setCurrentUser(user);

        // 🔥 LOAD WISHLIST AFTER LOGIN
        await loadWishlist(user);

         await fetchCartCount(user);

      } else {

        localStorage.removeItem("token");

        setCurrentUser(null);

        setCartCount(0);

        setWishlistIds([]);
      }

      setLoading(false);

    });

    return unsubscribe;

  }, []);

  /**
   * 🔥 LOGOUT
   */
  const logout = async () => {

    await signOut(auth);

    localStorage.removeItem("token");

    setCurrentUser(null);

    setCartCount(0);

    setWishlistIds([]);
  };

  return (
    <AuthContext.Provider
      value={{
        currentUser,
        logout,
        wishlistIds,
        setWishlistIds,
        loadWishlist,
        cartCount,
        setCartCount,
        fetchCartCount
      }}
    >
      {!loading && children}
    </AuthContext.Provider>
  );
};