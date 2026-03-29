import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../css/Sidebar.css';

function Sidebar() {
  const [isLectureOpen, setIsLectureOpen] = useState(false);

  const toggleLectureMenu = () => {
    setIsLectureOpen(!isLectureOpen);
  };

  return (
    <aside className="sidebar">
      <nav>
        <ul>
          <li><Link to="/notices">공지사항</Link></li>
          <li><Link to="/restaurants">식당메뉴</Link></li>

          <li className="dropdown">
            <span className="menu-title">수강신청</span>
            <ul className="submenu">
              <li><Link to="/lectures" >강의관리</Link></li>
              <li><Link to="/enrollments">수강신청</Link></li>
              <li><Link to="/my-enrollments">내 수강신청 목록</Link></li>
              <li><Link to="/subjects">과목관리</Link></li>
              <li><Link to="/classrooms">강의실 관리</Link></li>
              <li><Link to="/schedules">시간표 관리</Link></li>
            </ul>
          </li>
          <li><Link to="/grade-info">성적조회</Link></li>
          <li><Link to="/posts">게시판</Link></li>
          <li><Link to="/library">도서관</Link></li>
          <li><Link to="/map">지도</Link></li>

        </ul>
      </nav>
    </aside>
  );
}

export default Sidebar;