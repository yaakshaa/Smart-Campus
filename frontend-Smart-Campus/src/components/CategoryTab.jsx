import React from "react";

function CategoryTab({ categories, selected, onSelect }) {
  return (
    <div
      className="category-tab"
      style={{
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "flex-start", // ← 왼쪽 정렬
        alignItems: "center",
        gap: "6px",
        marginBottom: "12px",
        paddingLeft: "8px", // 약간의 왼쪽 여백
      }}
    >
      {categories.map((cat) => (
        <button
          key={cat}
          type="button"
          onClick={() => onSelect(cat)}
          style={{
            padding: "6px 14px",           // ← 버튼 크기 약간 키움
            fontSize: "14px",              // ← 폰트 크기 약간 키움
            lineHeight: "1.4",
            borderRadius: "16px",
            border: "1px solid",
            borderColor: cat === selected ? "#007bff" : "#ccc",
            backgroundColor: cat === selected ? "#007bff" : "#fff",
            color: cat === selected ? "#fff" : "#333",
            fontWeight: cat === selected ? "600" : "normal",
            cursor: "pointer",
            transition: "all 0.2s ease-in-out",
            boxShadow:
              cat === selected ? "0 2px 6px rgba(0,123,255,0.25)" : "none",
            margin: "0",
          }}
          onMouseEnter={(e) => {
            if (cat !== selected) e.target.style.backgroundColor = "#f1f1f1";
          }}
          onMouseLeave={(e) => {
            if (cat !== selected) e.target.style.backgroundColor = "#fff";
          }}
        >
          {cat}
        </button>
      ))}
    </div>
  );
}

export default CategoryTab;
