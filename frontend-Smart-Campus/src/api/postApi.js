// src/api/postApi.js
import axios from "axios";

axios.defaults.withCredentials = true;

const BASE_URL = "http://localhost:8080";

//  전체 게시글 조회
export const fetchPosts = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/post`);
    return response.data.data;
  } catch (error) {
    console.error("게시글 목록 불러오기 실패", error);
    return [];
  }
};

//  카테고리별 게시글 조회
export const fetchPostsByCategory = async (categoryName) => {
  try {
    const response = await axios.get(`${BASE_URL}/post?category=${categoryName}`);
    return response.data.data;
  } catch (error) {
    console.error("카테고리별 게시글 불러오기 실패", error);
    return [];
  }
};

//  게시글 작성
export const createPost = async (postDTO) => {
   console.log("보낼 데이터:", postDTO);
  try {
    const response = await axios.post(`${BASE_URL}/post`, postDTO, {
      withCredentials: true, 
    });
    return response.data;
  } catch (error) {
    console.error("게시글 등록 실패", error);
    return null;
  }
};

//  게시글 단건 조회
export const fetchPost = async (postNo) => {
  try {
    const response = await axios.get(`${BASE_URL}/post/${postNo}`, {
      withCredentials: true,
    });
    return response.data.data;
  } catch (error) {
    console.error("게시글 불러오기 실패", error);
    throw error;
  }
};

//게시글 수정
export const updatePost = async (postNo, postDTO) => {
  try {
    const response = await axios.put(`${BASE_URL}/post/${postNo}`, postDTO, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("게시글 수정 실패", error);
    return null;
  }
};

// 게시글 삭제
export const deletePost = async (postNo) => {
  try {
    const response = await axios.delete(
      `http://localhost:8080/post/${postNo}`,
      {
        withCredentials: true, 
      }
    );
    return response.data;
  } catch (error) {
    console.error("게시글 삭제 실패", error);
    throw error;
  }
};

//  좋아요 수 조회
export const getPostLikeCount = async (postNo) => {
  try {
    const response = await axios.get(`${BASE_URL}/postlike/count/${postNo}`, {
      withCredentials: true,
    });
    return response.data.data;
  } catch (error) {
    console.error("좋아요 수 불러오기 실패", error);
    return 0;
  }
};

//  게시글 좋아요 누르기
export const likePost = async (postNo) => {
  try {
    const response = await axios.post(
      `http://localhost:8080/postlike/${postNo}`,
      null,
      {
        withCredentials: true, 
      }
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};
