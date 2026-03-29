// src/pages/SchedulePage.jsx
import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import { fetchSchedules } from '../api/scheduleApi';
import ScheduleList from '../components/ScheduleList';
import ScheduleForm from '../components/ScheduleForm';
import MainLayout from '../layout/MainLayout'; // ✅ 추가
import '../css/LectureHeader.css';

const SchedulePage = () => {
  const [schedules, setSchedules] = useState([]);

  const loadSchedules = async () => {
    const data = await fetchSchedules();
    setSchedules(data);
  };

  useEffect(() => {
    loadSchedules();
  }, []);

  return (
    <MainLayout>
      <div className="p-4">
        {/* 상단 강의 링크 */}
        <nav className="lecture-nav">
          <Link to="/lectures">강의 관리</Link>
          <Link to="/enrollments">수강신청 (새로운 강의)</Link>
          <Link to="/my-enrollments">내 수강신청 목록</Link>
          <Link to="/subjects">과목 관리</Link>
          <Link to="/classrooms">강의실 관리</Link>
          <Link to="/schedules">시간표 관리</Link>
        </nav>

        <h1 className="text-2xl font-bold mb-4">시간표 관리</h1>
        <ScheduleForm onSuccess={loadSchedules} />
        <ScheduleList schedules={schedules} />
      </div>
    </MainLayout>
  );
};

export default SchedulePage;
