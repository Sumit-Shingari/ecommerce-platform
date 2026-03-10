import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyCO9R2xuk7jn10TjzeSijHzZdGVi4tYGc4",
  authDomain: "e-commerce-1f22c.firebaseapp.com",
  projectId: "e-commerce-1f22c",
  storageBucket: "e-commerce-1f22c.firebasestorage.app",
  messagingSenderId: "249872154218",
  appId: "1:249872154218:web:758643641219bc024dd413",
  measurementId: "G-FECGKXPM33"
};

const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);