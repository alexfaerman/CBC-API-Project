import axios from "axios";

const BASE_URL = "http://localhost:8080/items";

export const createItem = async (data, source) => {
  return axios.post(`${BASE_URL}?ContentSource=${source}`, data);
};

export const bulkUpload = async (data) => {
  return axios.post(`${BASE_URL}/bulk`, data);
};

export const getItemById = async (id) => {
  return axios.get(`${BASE_URL}/${id}`);
};

export const getAllItems = async () => {
  return axios.get(BASE_URL); 
};
