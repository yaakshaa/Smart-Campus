// src/api/commentApi.js
import axios from "axios";

axios.defaults.withCredentials = true;

const BASE_URL = "http://localhost:8080";

//  댓글 목록 가져오기
export const fetchCommentsByPostNo = async (postNo) => {
  try {
    const response = await axios.get(`${BASE_URL}/comment/${postNo}`);
    return response.data.data;
  } catch (error) {
    console.error("댓글 불러오기 실패:", error);
    return [];
  }
};

// 댓글 작성
export const createComment = async (commentDTO) => {
  try {
    const response = await axios.post(`${BASE_URL}/comment`, commentDTO, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    console.error("댓글 작성 실패:", error);
    throw error;
  }
};

//  댓글 삭제
export const deleteComment = async (commentNo) => {
  try {
    const response = await axios.delete(`${BASE_URL}/comment/${commentNo}`);
    return response.data;
  } catch (error) {
    console.error("댓글 삭제 실패:", error);
    throw error;
  }
};

//  댓글 좋아요
export const createCommentLike = async ({ commentNo }) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/comment-like`,
      { commentNo },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("댓글 좋아요 실패:", error);
    throw error;
  }
};

export const likeComment = createCommentLike;
