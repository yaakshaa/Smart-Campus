import React from 'react';
import { useNavigate } from 'react-router-dom';

const MenuCard = ({ menu, isAdmin, onEdit, onDelete }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/menu/${menu.menuId}/reviews`);
  };

  return (
    <div
      className="menu-card"
      onClick={handleClick}
    >
      <div className="menu-card-content">
        <div>
          <strong className="menu-food-name">{menu.foodName}</strong>
          {menu.reviewPreview && (
            <p className="menu-preview">"{menu.reviewPreview}"</p>
          )}
        </div>

        {isAdmin && (
          <div className="menu-card-buttons">
            <button className="icon-btn" onClick={(e) => {
              e.stopPropagation();
              onEdit(menu.menuId);
            }}>✏️</button>
            <button className="icon-btn" onClick={(e) => {
              e.stopPropagation();
              onDelete(menu.menuId);
            }}>🗑️</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default MenuCard;
