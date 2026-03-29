import React, { useState } from "react";
import ReviewList from "../components/ReviewList";
import ReviewStarInput from "../components/ReviewStarInput"; // ✅ 추가
import "../css/MenuDetailPage.css";
import "../css/ReviewStarInput.css"; // ✅ 별점 CSS도 추가

const MenuDetailPage = ({ menu, reviews, isAdmin, currentUserId, onDeleteReview, onAddReview, hasUserReviewed }) => {
  const [newComment, setNewComment] = useState("");
  const [newRating, setNewRating] = useState(5);

  if (!menu) return <p>메뉴 정보가 없습니다.</p>;

  const handleSubmit = () => {
    if (!currentUserId) {
      alert("로그인 후 리뷰를 작성하실 수 있습니다.");
      return;
    }

    if (!newComment.trim()) {
      alert("리뷰 내용을 입력해주세요.");
      return;
    }

    const newReview = {
      menuId: menu.menuId,
      rating: newRating,
      reviewcomment: newComment,
      createdAt: new Date().toISOString()
    };

    onAddReview(newReview);
    setNewComment("");
    setNewRating(5);
  };

  const averageRating =
    reviews.length > 0
      ? (reviews.reduce((sum, r) => sum + r.rating, 0) / reviews.length).toFixed(1)
      : "없음";

  return (
    <div className="menu-detail-container">
      <h2 className="menu-title">{menu.foodName}</h2>
      <p className="menu-info">칼로리: {menu.calorie} kcal</p>
      <p className="menu-info">알레르기: {menu.allergyInfo}</p>
      <p className="menu-rating">⭐ 평균 별점: {averageRating}</p>

      <hr />

      <h3 className="section-title">리뷰 등록</h3>
      {hasUserReviewed ? (
        <p className="already-comment">이미 리뷰를 작성하셨습니다.</p>
      ) : (
        <>
          <div className="review-form">
            <label>별점: </label>
            <ReviewStarInput rating={newRating} setRating={setNewRating} />
          </div>
          <textarea
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            rows={3}
            className="review-textarea"
            placeholder="리뷰를 입력하세요..."
          />
          <br />
          <button className="review-submit-btn" onClick={handleSubmit}>
            등록
          </button>
        </>
      )}

      <hr />

      <h3 className="section-title">리뷰 목록</h3>
      {reviews.length === 0 ? (
        <p className="no-review">리뷰가 없습니다.</p>
      ) : (
        <ReviewList
          reviews={reviews}
          currentUserId={currentUserId}
          isAdmin={isAdmin}
          onDelete={onDeleteReview}
        />
      )}
    </div>
  );
};

export default MenuDetailPage;
