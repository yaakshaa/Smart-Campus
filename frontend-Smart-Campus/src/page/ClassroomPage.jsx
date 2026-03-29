// src/pages/ClassroomPage.jsx
import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import { fetchClassrooms } from '../api/classroomApi';
import ClassroomForm from '../components/ClassroomForm';
import ClassroomList from '../components/ClassroomList';
import MainLayout from '../layout/MainLayout'; // ✅ 추가
import '../css/LectureHeader.css';

const ClassroomPage = () => {
  const [classrooms, setClassrooms] = useState([]);

  const loadClassrooms = async () => {
    const data = await fetchClassrooms();
    setClassrooms(data);
  };

  useEffect(() => {
    loadClassrooms();
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
        <h1 className="text-2xl font-bold mb-4">강의실 관리</h1>
        <ClassroomForm onSuccess={loadClassrooms} />
        <ClassroomList classrooms={classrooms} />
      </div>
    </MainLayout>
  );
};

export default ClassroomPage;
