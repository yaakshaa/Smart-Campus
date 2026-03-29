import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import * as postApi from "../api/postApi";
import * as categoryApi from "../api/categoryApi";
import CategoryTab from "../components/CategoryTab";
import PostList from "../components/PostList";
import CategoryAdminPanel from "../components/CategoryAdminPanel";

import Header from "../layout/Header";
import Sidebar from "../layout/Sidebar";
import Footer from "../layout/Footer";

function PostListPage() {
  const [posts, setPosts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [rawCategories, setRawCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("전체");

  const [newCategoryName, setNewCategoryName] = useState("");
  const [editCategoryNo, setEditCategoryNo] = useState(null);
  const [editCategoryName, setEditCategoryName] = useState("");

  const sUserInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const isAdmin = sUserInfo?.role?.toLowerCase() === "admin";

  // ✅ categoryNo 오름차순 정렬 (등록순)
  const loadCategories = async () => {
    const data = await categoryApi.fetchCategories();
    if (Array.isArray(data)) {
      const sorted = [...data].sort((a, b) => a.categoryNo - b.categoryNo); // 등록순 정렬
      setRawCategories(sorted);
      const categoryNames = sorted.map((cat) => cat.categoryName);
      setCategories(["전체", ...categoryNames]);
    }
  };

  useEffect(() => {
    loadCategories();
  }, []);

  // 게시글 목록 불러오기
  useEffect(() => {
    const fetchPosts = async () => {
      let data = [];
      if (selectedCategory === "전체") {
        data = await postApi.fetchPosts();
      } else {
        data = await postApi.fetchPostsByCategory(selectedCategory);
      }
      setPosts(Array.isArray(data) ? data : []);
    };
    fetchPosts();
  }, [selectedCategory]);

  // 카테고리 추가
  const handleAddCategory = async () => {
    if (!newCategoryName.trim()) return;
    try {
      await categoryApi.createCategory({ categoryName: newCategoryName });
      setNewCategoryName("");
      loadCategories();
    } catch (e) {
      alert("카테고리 추가 실패");
    }
  };

  // 카테고리 수정
  const handleUpdateCategory = async () => {
    if (!editCategoryNo || !editCategoryName.trim()) return;
    try {
      await categoryApi.updateCategory(editCategoryNo, editCategoryName);
      setEditCategoryNo(null);
      setEditCategoryName("");
      loadCategories();
    } catch (e) {
      alert("카테고리 수정 실패");
    }
  };

  // 카테고리 삭제
  const handleDeleteCategory = async (categoryNo) => {
    const ok = window.confirm("정말 삭제하시겠습니까?");
    if (!ok) return;
    try {
      await categoryApi.deleteCategory(categoryNo);
      setEditCategoryNo(null);
      loadCategories();
    } catch (e) {
      alert("카테고리 삭제 실패");
    }
  };

  return (
    <div>
      <Header />
      <div style={{ display: "flex" }}>
        <Sidebar />
        <main style={{ flex: 1, padding: "24px", minWidth: 0 }}>
          <h2 style={{ fontSize: "30px", fontWeight: "600", marginBottom: "16px" }}>
            📋 게시판
          </h2>

          {/* 🔹 카테고리 탭 */}
          <CategoryTab
            categories={categories}
            selected={selectedCategory}
            onSelect={setSelectedCategory}
          />

          {/* 🔹 관리자만 카테고리 관리 UI 사용 */}
          {isAdmin && (
            <CategoryAdminPanel
              categories={rawCategories}
              newCategoryName={newCategoryName}
              setNewCategoryName={setNewCategoryName}
              editCategoryNo={editCategoryNo}
              setEditCategoryNo={setEditCategoryNo}
              editCategoryName={editCategoryName}
              setEditCategoryName={setEditCategoryName}
              onAdd={handleAddCategory}
              onUpdate={handleUpdateCategory}
              onDelete={handleDeleteCategory}
            />
          )}

          {/* 🔹 게시글 목록 */}
          <PostList posts={posts} />

          {/* 🔹 글쓰기 버튼 */}
          {sUserInfo?.userId && (
            <div style={{ textAlign: "right", margin: "16px 0" }}>
              <Link to="/posts/write">
                <button
                  style={{
                    padding: "8px 16px",
                    fontSize: "14px",
                    backgroundColor: "#002b5c",
                    color: "white",
                    border: "none",
                    borderRadius: "6px",
                    fontWeight: "bold",
                    cursor: "pointer",
                  }}
                >
                  쓰기
                </button>
              </Link>
            </div>
          )}
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default PostListPage;
