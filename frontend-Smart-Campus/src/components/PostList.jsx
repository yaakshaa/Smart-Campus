import React from "react";
import { Link } from "react-router-dom";
import dayjs from "dayjs";

function formatPostDate(dateString) {
  const now = dayjs();
  const date = dayjs(dateString);
  return now.format("YYYY-MM-DD") === date.format("YYYY-MM-DD")
    ? date.format("HH:mm")
    : date.format("YYYY-MM-DD");
}

function PostList({ posts }) {
  return (
    <table style={{ width: "100%", borderCollapse: "collapse", fontSize: "14px" }}>
      <thead>
        <tr style={{ backgroundColor: "#f2f2f2" }}>
          <th style={{ padding: "8px" }}>번호</th>
          <th style={{ padding: "8px" }}>카테고리</th>
          <th style={{ padding: "8px" }}>제목</th>
          <th style={{ padding: "8px" }}>작성자</th>
          <th style={{ padding: "8px" }}>날짜</th>
          <th style={{ padding: "8px" }}>조회수</th>
          <th style={{ padding: "8px" }}>좋아요</th>
        </tr>
      </thead>
      <tbody>
        {posts.length === 0 ? (
          <tr>
            <td colSpan="7" align="center">
              <div
                style={{
                  padding: "60px 0", // 위아래 여백 추가
                  color: "#555",
                  fontSize: "15px"
                }}
              >
                게시글이 없습니다.
              </div>
            </td>
          </tr>
        ) : (
          posts.map((post) => (
            <tr key={post.postNo} style={{ borderBottom: "1px solid #eee" }}>
              <td style={{ padding: "8px", textAlign: "center" }}>{post.postNo}</td>
              <td style={{ padding: "8px", textAlign: "center" }}>{post.categoryName}</td>
              <td style={{ padding: "8px" }}>
                <Link
                  to={`/posts/${post.postNo}`}
                  style={{
                    color: "black",
                    textDecoration: "none"
                  }}
                  onMouseOver={(e) => (e.target.style.textDecoration = "underline")}
                  onMouseOut={(e) => (e.target.style.textDecoration = "none")}
                >
                  {post.postTitle}
                </Link>
                {post.commentCount > 0 && (
                  <span style={{ color: "gray", marginLeft: "6px" }}>
                    [{post.commentCount}]
                  </span>
                )}
              </td>
              <td style={{ padding: "8px", textAlign: "center" }}>{post.userId}</td>
              <td style={{ padding: "8px", textAlign: "center" }}>
                {formatPostDate(post.postCreated)}
              </td>
              <td style={{ padding: "8px", textAlign: "center" }}>{post.postView}</td>
              <td style={{ padding: "8px", textAlign: "center" }}>{post.postLikeCount}</td>
            </tr>
          ))
        )}
      </tbody>
    </table>
  );
}

export default PostList;

