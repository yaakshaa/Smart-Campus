import React, { useState, useEffect } from 'react';
import "../css/MenuForm.css";

const EditMenuForm = ({ menu, onSubmit }) => {
  const [form, setForm] = useState({
    foodName: '',
    mealType: '',
    date: '',
    calorie: '',
    allergyInfo: ''
  });

  useEffect(() => {
    if (menu) {
      setForm({
        foodName: menu.foodName || '',
        mealType: menu.mealType || '',
        date: menu.date || '',
        calorie: menu.calorie || '',
        allergyInfo: menu.allergyInfo || ''
      });
    }
  }, [menu]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="menu-form-container">
      <h2>메뉴 수정</h2>

      <div className="menu-form-labels">
        <div>🍱 메뉴 이름</div>
        <div>⏰ 식사 시간</div>
        <div>📅 날짜</div>
        <div>🔥 칼로리</div>
        <div>⚠️ 알레르기</div>
      </div>

      <div className="menu-form-row">
        <input
          name="foodName"
          placeholder="메뉴 이름"
          value={form.foodName}
          onChange={handleChange}
          required
        />
        <select name="mealType" value={form.mealType} onChange={handleChange} required>
          <option value="">선택</option>
          <option value="아침">아침</option>
          <option value="점심">점심</option>
          <option value="저녁">저녁</option>
        </select>
        <input type="date" name="date" value={form.date} onChange={handleChange} required />
        <input type="number" name="calorie" value={form.calorie} onChange={handleChange} />
        <input name="allergyInfo" value={form.allergyInfo} onChange={handleChange} />
      </div>

      <div className="menu-btn-group">
        <button type="submit">수정 완료</button>
      </div>
    </form>
  );
};

export default EditMenuForm;
