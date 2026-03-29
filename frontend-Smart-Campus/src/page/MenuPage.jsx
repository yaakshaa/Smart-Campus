import React from "react";
import MenuCard from "../components/MenuCard";
import "../css/MenuPage.css";

const MenuPage = ({ menus = [], isAdmin, onEditMenu, onDeleteMenu }) => {
  const mealTypes = [
    { label: "아침", time: "07:00~09:00" },
    { label: "점심", time: "12:00~14:00" },
    { label: "저녁", time: "17:00~19:00" }
  ];

  return (
    <div className="menu-page-container">
      <h2 className="menu-page-title">식사 시간별 메뉴</h2>

      <div className="meal-sections">
        {mealTypes.map((type) => {
          const filteredByMeal = menus.filter((menu) => menu.mealType === type.label);
          return (
            <div className="meal-section" key={type.label}>
              <h3 className="meal-title">
                {type.label}
                <div className="meal-time">{type.time} 운영</div>
              </h3>
              <div className="menu-card-list">
                {filteredByMeal.length > 0 ? (
                  filteredByMeal.map((menu) => (
                    <MenuCard
                      key={menu.menuId}
                      menu={menu}
                      isAdmin={isAdmin}
                      onEdit={onEditMenu}
                      onDelete={onDeleteMenu}
                    />
                  ))
                ) : (
                  <p className="no-menu-text">등록된 메뉴가 없습니다.</p>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default MenuPage;
