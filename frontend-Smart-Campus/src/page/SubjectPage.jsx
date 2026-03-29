// src/pages/SubjectPage.jsx
import React, { useEffect, useState } from 'react';
import { fetchSubjects } from '../api/subjectApi';
import SubjectList from '../components/SubjectList';
import SubjectForm from '../components/SubjectForm';
import { Link } from "react-router-dom";
import MainLayout from '../layout/MainLayout'; // ✅ 추가
import '../css/LectureHeader.css';

const SubjectPage = () => {
  const [subjects, setSubjects] = useState([]);

  const loadSubjects = async () => {
    const data = await fetchSubjects();
    setSubjects(data);
  };

  useEffect(() => {
    loadSubjects();
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
        <h1 className="text-2xl font-bold mb-4">과목 관리</h1>
        <SubjectForm onSuccess={loadSubjects} />
        <SubjectList subjects={subjects} />
      </div>
    </MainLayout>
  );
};

export default SubjectPage;
