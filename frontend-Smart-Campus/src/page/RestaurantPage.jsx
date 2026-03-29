import React, { useState } from "react";
import RestaurantCard from "../components/RestaurantCard";
import "../css/RestaurantPage.css";

const RestaurantPage = ({ restaurants, isAdmin }) => {
  const [selectedDate, setSelectedDate] = useState(getToday());

  const changeDate = (delta) => {
    const newDate = new Date(selectedDate);
    newDate.setDate(newDate.getDate() + delta);
    setSelectedDate(newDate.toISOString().split("T")[0]);
  };

  const getDayOfWeek = (dateStr) => {
    const days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    const date = new Date(dateStr);
    return days[date.getDay()];
  };

  return (
    <div className="restaurant-page-container">
      <div className="restaurant-header">
        <div className="restaurant-date-nav">
          <div className="date-top-row">
            <button onClick={() => changeDate(-1)}>←</button>
            <div className="restaurant-date">{selectedDate.slice(5)}</div>
            <button onClick={() => changeDate(1)}>→</button>
          </div>
          <div className="restaurant-day">{getDayOfWeek(selectedDate)}</div>
        </div>
      </div>

      <h2 className="restaurant-title">식당 목록</h2>
      <div className="restaurant-list">
        {restaurants.map((restaurant) => (
          <div className="restaurant-card" key={restaurant.restaurantId}>
            <RestaurantCard
              restaurant={restaurant}
              isAdmin={isAdmin}
              selectedDate={selectedDate}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

function getToday() {
  const today = new Date();
  return today.toISOString().split("T")[0];
}

export default RestaurantPage;
