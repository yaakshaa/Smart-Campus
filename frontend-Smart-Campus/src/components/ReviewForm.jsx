import React, { useState } from 'react';
import '../css/Review.css'; // 또는 MenuDetailPage.css 경로에 따라 조정

const ReviewForm = ({ menuId, onSubmit }) => {
  const [form, setForm] = useState({ rating: 5, reviewcomment: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ ...form, menuId });
    setForm({ rating: 5, reviewcomment: '' });
  };

  return (
    <form onSubmit={handleSubmit} className="review-form">
      <label>별점: </label>
      <select name="rating" value={form.rating} onChange={handleChange}>
        {[5, 4, 3, 2, 1].map(n => (
          <option key={n} value={n}>{n}점</option>
        ))}
      </select>
      <textarea
        name="reviewcomment"
        placeholder="리뷰를 입력하세요..."
        value={form.reviewcomment}
        onChange={handleChange}
        required
        className="review-textarea"
      />
      <button type="submit">등록</button>
    </form>
  );
};

export default ReviewForm;
