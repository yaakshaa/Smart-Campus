import React from 'react';
import { useNavigate } from 'react-router-dom';

const RestaurantCard = ({ restaurant, isAdmin, selectedDate }) => {
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/menu/${restaurant.id}?date=${selectedDate}`);

  };

  const handleAddMenuClick = (e) => {
    e.stopPropagation();
    navigate(`/menu/${restaurant.id}/add?date=${selectedDate}`);

  };

  // 선택된 날짜에 해당하는 메뉴들 필터링
  const todayMenus = restaurant.menus
    ?.filter(menu => menu.date === selectedDate)
    .map(menu => menu.foodName)
    .join(', ');

  return (
    <div
      className="restaurant-card"
      onClick={handleCardClick}
      style={{
        cursor: "pointer",
        border: "1px solid #ccc",
        padding: "10px",
        marginBottom: "10px"
      }}
    >
      <h3>{restaurant.name}</h3>
      <p>{restaurant.location}</p>
      <p>오늘의 메뉴: {todayMenus || '등록된 메뉴 없음'}</p>

      {isAdmin && (
        <button onClick={handleAddMenuClick}>+ 메뉴 등록</button>
      )}
    </div>
  );
};

export default RestaurantCard;
