// src/api/categoryApi.js
import axios from "axios";

const BASE_URL = "http://localhost:8080";

// 🔸 전체 카테고리 조회
export const fetchCategories = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/category`);
    return response.data.data; // 카테고리 배열만 반환
  } catch (error) {
    console.error("카테고리 불러오기 실패", error);
    return [];
  }
};

// 🔸 카테고리 등록
export const createCategory = async (categoryDTO) => {
  try {
    const response = await axios.post(`${BASE_URL}/category`, categoryDTO);
    return response.data.data;
  } catch (error) {
    console.error("카테고리 등록 실패", error);
    throw error;
  }
};

// 🔸 카테고리 수정
export const updateCategory = async (categoryNo, newCategoryName) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/category/${categoryNo}?newCategoryName=${encodeURIComponent(newCategoryName)}`
    );
    return response.data.data;
  } catch (error) {
    console.error("카테고리 수정 실패", error);
    throw error;
  }
};

// 🔸 카테고리 삭제
export const deleteCategory = async (categoryNo) => {
  try {
    const response = await axios.delete(`${BASE_URL}/category/${categoryNo}`);
    return response.data.data;
  } catch (error) {
    console.error("카테고리 삭제 실패", error);
    throw error;
  }
};
