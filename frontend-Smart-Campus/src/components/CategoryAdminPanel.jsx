import React from "react";

function CategoryAdminPanel({
  categories,
  newCategoryName,
  setNewCategoryName,
  editCategoryNo,
  setEditCategoryNo,
  editCategoryName,
  setEditCategoryName,
  onAdd,
  onUpdate,
  onDelete,
}) {
  return (
    <div
      style={{
        background: "#f9f9f9",
        padding: "16px 20px",
        border: "1px solid #ccc",
        borderRadius: "10px",
        marginBottom: "16px",
        fontSize: "14px",
      }}
    >
      <div
        style={{
          marginBottom: "12px",
          fontWeight: "600",
          display: "flex",
          alignItems: "center",
          gap: "8px",
        }}
      >
        🛠 카테고리 관리 <span style={{ color: "#888" }}>(관리자용)</span>
      </div>

      {/* 추가 */}
      <div style={{ display: "flex", gap: "8px", marginBottom: "10px" }}>
        <input
          type="text"
          placeholder="새 카테고리 이름"
          value={newCategoryName}
          onChange={(e) => setNewCategoryName(e.target.value)}
          style={{
            flex: 1,
            padding: "6px 10px",
            border: "1px solid #ccc",
            borderRadius: "6px",
          }}
        />
        <button
          onClick={onAdd}
          style={{
            padding: "6px 14px",
            backgroundColor: "#28a745",
            color: "white",
            border: "none",
            borderRadius: "6px",
            fontWeight: "bold",
            cursor: "pointer",
          }}
        >
          추가
        </button>
      </div>

      {/* 수정 */}
      <div style={{ display: "flex", gap: "8px", marginBottom: "10px" }}>
        <select
          value={editCategoryNo || ""}
          onChange={(e) => setEditCategoryNo(Number(e.target.value))}
          style={{
            width: "180px",
            padding: "6px 8px",
            border: "1px solid #ccc",
            borderRadius: "6px",
          }}
        >
          <option value="">카테고리 선택</option>
          {categories.map((cat) => (
            <option key={cat.categoryNo} value={cat.categoryNo}>
              {cat.categoryName} (No.{cat.categoryNo})
            </option>
          ))}
        </select>
        <input
          type="text"
          placeholder="새 이름"
          value={editCategoryName}
          onChange={(e) => setEditCategoryName(e.target.value)}
          style={{
            flex: 1,
            padding: "6px 10px",
            border: "1px solid #ccc",
            borderRadius: "6px",
          }}
        />
        <button
          onClick={onUpdate}
          style={{
            padding: "6px 14px",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "6px",
            fontWeight: "bold",
            cursor: "pointer",
          }}
        >
          수정
        </button>
      </div>

      {/* 삭제 */}
      <div style={{ display: "flex", gap: "8px" }}>
        <select
          value={editCategoryNo || ""}
          onChange={(e) => setEditCategoryNo(Number(e.target.value))}
          style={{
            width: "180px",
            padding: "6px 8px",
            border: "1px solid #ccc",
            borderRadius: "6px",
          }}
        >
          <option value="">카테고리 선택</option>
          {categories.map((cat) => (
            <option key={cat.categoryNo} value={cat.categoryNo}>
              {cat.categoryName} (No.{cat.categoryNo})
            </option>
          ))}
        </select>
        <button
          onClick={() => onDelete(editCategoryNo)}
          style={{
            padding: "6px 14px",
            backgroundColor: "#dc3545",
            color: "white",
            border: "none",
            borderRadius: "6px",
            fontWeight: "bold",
            cursor: "pointer",
          }}
        >
          삭제
        </button>
      </div>
    </div>
  );
}

export default CategoryAdminPanel;
