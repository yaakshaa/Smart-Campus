import React, { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { createMenu } from '../api/menuApi';
import "../css/MenuForm.css";

const AddMenuForm = ({ restaurantId }) => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const selectedDate = searchParams.get("date") || new Date().toISOString().split("T")[0];

  const [menus, setMenus] = useState([
    { foodName: '', mealType: '', date: selectedDate, calorie: '', allergyInfo: '' }
  ]);

  const handleChange = (index, e) => {
    const { name, value } = e.target;
    const updatedMenus = [...menus];
    updatedMenus[index][name] = value;
    setMenus(updatedMenus);
  };

  const addMenuForm = () => {
    setMenus([...menus, { foodName: '', mealType: '', date: selectedDate, calorie: '', allergyInfo: '' }]);
  };

  const removeMenuForm = (index) => {
    setMenus(menus.filter((_, i) => i !== index));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await Promise.all(
        menus.map(menu =>
          createMenu({ ...menu, restaurantId: Number(restaurantId) })
        )
      );
      alert("모든 메뉴가 등록되었습니다.");
      navigate(`/menu/${restaurantId}?date=${selectedDate}`);
    } catch (err) {
      alert("등록 실패: " + (err.response?.data?.message || err.message));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="menu-form-container">
      <h2>메뉴 등록</h2>

      <div className="menu-form-labels">
        <div>🍱 메뉴 이름</div>
        <div>⏰ 식사 시간</div>
        <div>📅 날짜</div>
        <div>🔥 칼로리</div>
        <div>⚠️ 알레르기</div>
      </div>

      {menus.map((menu, index) => (
        <div className="menu-form-row" key={index}>
          <input name="foodName" value={menu.foodName} onChange={(e) => handleChange(index, e)} required />
          <select name="mealType" value={menu.mealType} onChange={(e) => handleChange(index, e)} required>
            <option value="">선택</option>
            <option value="아침">아침</option>
            <option value="점심">점심</option>
            <option value="저녁">저녁</option>
          </select>
          <input type="date" name="date" value={menu.date} onChange={(e) => handleChange(index, e)} required />
          <input name="calorie" type="number" value={menu.calorie} onChange={(e) => handleChange(index, e)} />
          <input name="allergyInfo" value={menu.allergyInfo} onChange={(e) => handleChange(index, e)} />

          {/* ❌ 버튼 or 투명한 자리 유지용 span */}
          {menus.length > 1 ? (
            <button type="button" onClick={() => removeMenuForm(index)}>×</button>
          ) : (
            <span className="invisible-button">×</span>
          )}
        </div>
      ))}

      <div className="menu-btn-group">
        <button type="button" onClick={addMenuForm}>➕ 메뉴 추가</button>
        <button type="submit">등록</button>
      </div>
    </form>
  );
};

export default AddMenuForm;
