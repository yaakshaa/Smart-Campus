import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import * as commentApi from "../api/commentApi";
import dayjs from "dayjs";

function CommentSection({ postNo }) {
  const [comments, setComments] = useState([]);
  const [commentContent, setCommentContent] = useState("");

  const navigate = useNavigate();

  const sessionUserInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const sessionUserId = sessionUserInfo?.userId;
  const sessionUserType = sessionUserInfo?.role;

  const isLoggedIn = !!sessionUserId;
  const isAdmin = sessionUserType === "ADMIN";

  const checkLogin = () => {
    if (!isLoggedIn) {
      alert("로그인 후 이용해주세요.");
      navigate("/login");
      return false;
    }
    return true;
  };

  const loadComments = async () => {
    try {
      const data = await commentApi.fetchCommentsByPostNo(postNo);
      setComments(data);
    } catch (error) {
      console.error("댓글 불러오기 실패", error);
    }
  };

  useEffect(() => {
    loadComments();
  }, [postNo]);

  const handleRegister = async () => {
    if (!checkLogin()) return;
    if (!commentContent.trim()) {
      alert("내용을 입력해주세요.");
      return;
    }

    const newComment = { commentContent, postNo };

    try {
      const result = await commentApi.createComment(newComment);
      if (result) {
        setCommentContent("");
        loadComments();
      }
    } catch (error) {
      console.error("댓글 등록 실패", error);
      if (error.response?.status === 401) {
        alert("로그인이 필요합니다.");
        navigate("/login");
      } else {
        alert("댓글 등록 중 오류가 발생했습니다.");
      }
    }
  };

  const handleDelete = async (commentNo) => {
    if (!checkLogin()) return;

    const ok = window.confirm("댓글을 삭제하시겠습니까?");
    if (!ok) return;

    try {
      await commentApi.deleteComment(commentNo);
      loadComments();
    } catch (error) {
      console.error("댓글 삭제 실패", error);
      if (error.response?.status === 401) {
        alert("로그인이 필요합니다.");
        navigate("/login");
      } else {
        alert("삭제 실패: " + (error.response?.data?.message || "오류 발생"));
      }
    }
  };

  const handleLike = async (commentNo) => {
    if (!checkLogin()) return;

    try {
      await commentApi.createCommentLike({ commentNo });
      loadComments();
    } catch (error) {
      const message = error?.response?.data?.message || "";
      if (error.response?.status === 401) {
        alert("로그인이 필요합니다.");
        navigate("/login");
      } else if (!message.includes("이미 좋아요")) {
        alert("좋아요 실패: " + message);
      }
    }
  };

  return (
    <div>
      <h4>댓글 {comments.length}개</h4>

      {comments.map((c) => (
        <div
          key={c.commentNo}
          style={{
            borderBottom: "1px solid #eee",
            padding: "12px 0",
          }}
        >
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <div>
              <div>
                <strong>{c.userId}</strong>
                <span
                  style={{
                    fontSize: "12px",
                    color: "#999",
                    marginLeft: "8px",
                  }}
                >
                  {dayjs(c.commentCreated).format("YYYY-MM-DD HH:mm")}
                </span>
              </div>
              <div style={{ marginTop: "4px" }}>{c.commentContent}</div>
            </div>

            <div
              style={{
                display: "flex",
                alignItems: "center",
                gap: "6px",
                marginLeft: "16px",
              }}
            >
              {/* 👍 좋아요 버튼 + 수 */}
              <button
                onClick={() => handleLike(c.commentNo)}
                style={{
                  fontSize: "12px",
                  padding: "4px 8px",
                  background: "#fce4ec",
                  border: "1px solid #f8bbd0",
                  borderRadius: "4px",
                  cursor: "pointer",
                  color: "#000",
                  display: "flex",
                  alignItems: "center",
                  gap: "4px",
                }}
              >
                <span role="img" aria-label="like">❤️</span>
                <span>{c.commentLikeCount}</span>
              </button>

              {/* 삭제 버튼: 로그인 + (본인 or 관리자) */}
              {isLoggedIn && (String(sessionUserId) === String(c.userId) || isAdmin) && (
                <button
                  onClick={() => handleDelete(c.commentNo)}
                  style={{
                    fontSize: "12px",
                    padding: "4px 8px",
                    background: "#c0392b",
                    border: "1px solid #ffab91",
                    borderRadius: "4px",
                    cursor: "pointer",
                    color: "#fff",
                  }}
                >
                  삭제
                </button>
              )}
            </div>
          </div>
        </div>
      ))}

      {/* 댓글 작성 영역 */}
      <div
        style={{
          border: "1px solid #ddd",
          borderRadius: "6px",
          padding: "12px",
          marginTop: "20px",
          backgroundColor: "#fcfcfc",
          fontSize: "14px",
        }}
      >
        <div style={{ fontWeight: "bold", marginBottom: "6px" }}>댓글 쓰기</div>
        <textarea
          rows={3}
          value={commentContent}
          onChange={(e) => setCommentContent(e.target.value)}
          style={{
            width: "100%",
            padding: "8px",
            fontSize: "14px",
            border: "1px solid #ccc",
            borderRadius: "4px",
            resize: "vertical",
          }}
        />
        <div style={{ textAlign: "right", marginTop: "8px" }}>
          <button
            onClick={handleRegister}
            style={{
              backgroundColor: "#1976d2",
              color: "#fff",
              padding: "6px 14px",
              fontSize: "13px",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
            }}
          >
            등록
          </button>
        </div>
      </div>
    </div>
  );
}

export default CommentSection;
