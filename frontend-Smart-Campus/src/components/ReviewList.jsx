import React from "react";
import ReviewItem from "./ReviewItem";
import "../css/Review.css"; // ✅ css 연결

const ReviewList = ({ reviews, currentUserId, isAdmin, onDelete }) => {
  return (
    <div className="review-list">
      {reviews.map((review) => (
        <ReviewItem
          key={review.reviewId}
          review={review}
          showDelete={isAdmin || review.userId === currentUserId}
          onDelete={() => onDelete(review.reviewId)}
        />
      ))}
    </div>
  );
};

export default ReviewList;
