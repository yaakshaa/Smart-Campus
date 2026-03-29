import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as categoryApi from "../api/categoryApi";
import * as postApi from "../api/postApi";

import Header from "../layout/Header";
import Sidebar from "../layout/Sidebar";
import Footer from "../layout/Footer";

function PostWritePage() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [categoryList, setCategoryList] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  const navigate = useNavigate();
  const { postNo } = useParams();
  const sUserInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const userId = sUserInfo?.userId;

  useEffect(() => {
    categoryApi.fetchCategories().then((data) => {
      if (Array.isArray(data) && data.length > 0) {
        setCategoryList(data);
        setSelectedCategory(data[0].categoryNo);
      }
    });
  }, []);

  useEffect(() => {
    if (postNo) {
      postApi.fetchPost(postNo).then((post) => {
        const sessionUserInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
        const sessionUserId = sessionUserInfo?.userId;
        const sessionUserType = sessionUserInfo?.userType;

        //  권한 검사
        const isOwner = post.userId === sessionUserId;
        const isAdmin = sessionUserType === "관리자";

        if (!isOwner && !isAdmin) {
          alert("본인 또는 관리자만 수정할 수 있습니다.");
          navigate(`/posts/${postNo}`);
          return;
        }

        //  권한 통과 → 데이터 세팅
        setTitle(post.postTitle);
        setContent(post.postContent);
        setSelectedCategory(post.categoryNo);
      });
    }
  }, [postNo]);

  const handleSubmit = async () => {
    if (!userId) {
      alert("로그인이 필요합니다.");
      return;
    }

    if (!title.trim() || !content.trim()) {
      alert("제목과 내용을 모두 입력해주세요.");
      return;
    }

    const postDTO = {
      postTitle: title,
      postContent: content,
      categoryNo: selectedCategory,
      userId: userId,
    };

    try {
      const result = postNo
        ? await postApi.updatePost(postNo, postDTO)
        : await postApi.createPost(postDTO);

      const success = result?.status === 3201 || result?.status === 3205;

      if (success) {
        alert(postNo ? "게시글이 수정되었습니다." : "게시글이 등록되었습니다.");
        navigate(`/posts/${result.data.postNo}`);
      } else {
        alert("처리 실패: " + (result?.message || "서버 오류"));
      }
    } catch (error) {
      console.error("게시글 등록 실패", error);
      alert("게시글 등록 중 오류가 발생했습니다.");
    }
  };

  return (
    <div>
      <Header />
      <div style={{ display: "flex" }}>
        <Sidebar />
        <main style={{ flex: 1, padding: "20px" }}>
          <div style={{ width: "800px", margin: "0 auto", padding: "24px" }}>
            <h2 style={{ marginBottom: "24px" }}>
              {postNo ? "게시글 수정" : "자유 게시판 작성"}
            </h2>

            <div style={{ display: "flex", alignItems: "center", marginBottom: "16px" }}>
              <select
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(Number(e.target.value))}
                style={{
                  width: "150px",
                  marginRight: "12px",
                  padding: "10px",
                  fontSize: "16px",
                  border: "1px solid #ccc",
                  borderRadius: "4px",
                  backgroundColor: "white",
                }}
              >
                {categoryList.map((cat) => (
                  <option key={cat.categoryNo} value={cat.categoryNo}>
                    {cat.categoryName}
                  </option>
                ))}
              </select>

              <input
                type="text"
                placeholder="제목"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                style={{
                  flex: 1,
                  padding: "10px",
                  fontSize: "16px",
                  border: "1px solid #ccc",
                  borderRadius: "4px",
                }}
              />
            </div>

            <textarea
              placeholder="내용을 입력하세요."
              value={content}
              onChange={(e) => setContent(e.target.value)}
              rows={15}
              style={{
                width: "100%",
                padding: "12px",
                fontSize: "15px",
                border: "1px solid #ccc",
                borderRadius: "4px",
                resize: "vertical",
              }}
            />

            <div style={{ textAlign: "right", marginTop: "16px" }}>
              <button
                onClick={handleSubmit}
                style={{
                  backgroundColor: "#5c6bc0",
                  color: "white",
                  padding: "10px 20px",
                  fontSize: "16px",
                  border: "none",
                  borderRadius: "4px",
                  cursor: "pointer",
                }}
              >
                {postNo ? "수정 완료" : "등록"}
              </button>
            </div>
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default PostWritePage;
