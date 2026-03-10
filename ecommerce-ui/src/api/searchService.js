import axios from "axios";

export const fetchSuggestions = async (keyword) => {
  if (!keyword || keyword.trim().length < 2) return [];

  const response = await axios.get(
    `http://localhost:8080/api/products/suggest?q=${keyword}`
  );

  return response.data;
};