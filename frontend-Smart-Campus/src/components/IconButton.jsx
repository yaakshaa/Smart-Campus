import React from "react";
import { useNavigate } from "react-router-dom";

function IconButton({ icon, imgSrc, title, to }) {
  const nav = useNavigate();

  return (
    <div className="lib-icon-item" onClick={() => nav(to)}>
      {imgSrc ? (
        <img src={imgSrc} alt={title} className="lib-icon-svg" style={{ width: 46, height: 46 }} />
      ) : (
        <span className="lib-icon-svg">{icon}</span>
      )}
      <span className="lib-icon-title">{title}</span>
    </div>
  );
}

export default IconButton;