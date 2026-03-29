import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import * as postApi from "../api/postApi";
import CommentSection from "../components/CommentSection";

import Header from "../layout/Header";
import Sidebar from "../layout/Sidebar";
import Footer from "../layout/Footer";

function PostViewPage() {
  const { postNo } = useParams();
  const navigate = useNavigate();

  const [post, setPost] = useState(null);
  const [likeCount, setLikeCount] = useState(0);

  const sessionUserInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const sessionUserId = sessionUserInfo?.userId;
  const sessionUserType = sessionUserInfo?.role;

  const fetchPostData = async () => {
    try {
      const data = await postApi.fetchPost(postNo);
      setPost(data);
      setLikeCount(data.postLikeCount);
    } catch (error) {
      alert("게시글을 불러오지 못했습니다.");
      navigate("/posts");
    }
  };

  useEffect(() => {
    fetchPostData();
  }, [postNo, navigate]);

 const handleLike = async () => {
  try {
    await postApi.likePost(postNo);
    setLikeCount(prev => prev + 1);
  } catch (error) {
    const msg = error?.response?.data?.message || "";
    if (!msg.includes("이미 좋아요")) {
      alert("오류: " + msg);
    }
  }
};

  const handleEdit = () => {
    navigate(`/posts/edit/${post.postNo}`);
  };

  const handleDelete = async () => {
    const ok = window.confirm("정말 삭제하시겠습니까?");
    if (!ok) return;

    try {
      await postApi.deletePost(postNo);
      alert("게시글이 삭제되었습니다.");
      navigate("/posts");
    } catch (error) {
      const status = error.response?.status;
      const message = error.response?.data?.message;

      if (status === 401 || status === 403) {
        alert(message || "본인 또는 관리자만 삭제할 수 있습니다.");
        navigate(`/posts/${postNo}`);
      } else {
        alert("삭제 중 오류가 발생했습니다.");
      }
    }
  };

  if (!post) return null;

  return (
    <div>
      <Header />
      <div style={{ display: "flex" }}>
        <Sidebar />
        <main
          style={{
            flex: 1,
            padding: "32px",
            maxWidth: "800px",
            margin: "0 auto",
            fontFamily: "'Noto Sans KR', sans-serif",
          }}
        >
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              marginBottom: "20px",
              borderBottom: "1px solid #ddd",
              paddingBottom: "10px",
            }}
          >
            <div>
              <span style={{ fontWeight: "bold", color: "#2c3e50" }}>
                [{post.categoryName}]
              </span>
              <span style={{ marginLeft: "10px", color: "#555" }}>
                {post.userId}
              </span>
            </div>
            <div style={{ fontSize: "14px", color: "#888" }}>
              {post.postCreated?.slice(0, 16).replace("T", " ")} | 조회수{" "}
              {post.postView} | 좋아요 {likeCount}
            </div>
          </div>

          <h2
            style={{
              fontSize: "24px",
              fontWeight: "700",
              marginBottom: "20px",
              color: "#34495e",
            }}
          >
            {post.postTitle}
          </h2>

          <div
            style={{
              border: "1px solid #ccc",
              borderRadius: "6px",
              padding: "20px",
              minHeight: "200px",
              marginBottom: "24px",
              backgroundColor: "#fdfdfd",
              lineHeight: "1.6",
              whiteSpace: "pre-wrap",
            }}
          >
            {post.postContent}
          </div>

          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              marginBottom: "24px",
            }}
          >
            <button
              onClick={handleLike}
              style={{
                backgroundColor: "#ff6b6b",
                color: "#fff",
                border: "none",
                padding: "8px 20px",
                borderRadius: "4px",
                fontWeight: "bold",
                cursor: "pointer",
              }}
            >
              ❤️ 좋아요 {likeCount}
            </button>

            {(String(sessionUserId) === String(post.userId) || sessionUserType === "ADMIN") && (
              <div style={{ display: "flex", gap: "10px" }}>
                <button
                  onClick={handleEdit}
                  style={{
                    padding: "6px 14px",
                    backgroundColor: "#2980b9",
                    color: "#fff",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  수정하기
                </button>
                <button
                  onClick={handleDelete}
                  style={{
                    padding: "6px 14px",
                    backgroundColor: "#c0392b",
                    color: "#fff",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  삭제하기
                </button>
              </div>
            )}
          </div>

          

          <CommentSection postNo={post.postNo} />
          <div style={{ textAlign: "center", marginBottom: "32px" }}>
            <button
              onClick={() => navigate("/posts")}
              style={{
                backgroundColor: "#2c3e50",
                color: "#fff",
                padding: "8px 20px",
                border: "none",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            >
              ← 목록
            </button>
          </div>
        </main>

        
      </div>
      <Footer />
    </div>
  );
}

export default PostViewPage;
