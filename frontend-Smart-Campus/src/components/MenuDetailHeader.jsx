import React from 'react';
const MenuDetailHeader = ({ menu }) => (
  <div>
    <h3>{menu.foodName}</h3>
    <p>{menu.menuDate} / {menu.mealType}</p>
    <p>칼로리: {menu.calorie}kcal / 알레르기: {menu.allergyInfo}</p>
    <p>⭐ 평균 별점: {menu.averageRating}</p>
  </div>
);
export default MenuDetailHeader;
