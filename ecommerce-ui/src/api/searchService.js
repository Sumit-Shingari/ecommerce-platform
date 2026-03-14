import api from "./axiosConfig";

export const fetchSuggestions = async (keyword) => {
  if (!keyword || keyword.trim().length < 2) return [];

  const response = await api.get(
    `/api/products/suggest?q=${keyword}`
  );

  return response.data;
};