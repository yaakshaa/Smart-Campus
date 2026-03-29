import React from "react";
import "../css/Review.css"; // ✅ css 연결

const ReviewItem = ({ review, showDelete, onDelete }) => {
  return (
    <div className="review-item">
      <div className="review-header">
        <span className="review-rating">⭐ {review.rating}</span>
        {showDelete && (
          <button className="review-delete" onClick={onDelete}>🗑️ 삭제</button>
        )}
      </div>
      <div className="review-content">{review.reviewcomment}</div>
      <div className="review-footer">
        <small>{review.userId} / {new Date(review.createdAt).toLocaleString()}</small>
      </div>
    </div>
  );
};

export default ReviewItem;
