import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import LectureList from '../components/LectureList';
import LectureForm from '../components/LectureForm';
import { fetchLectures, createLecture, updateLecture, deleteLecture } from '../api/lectureApi';
import { createEnrollment } from '../api/enrollmentApi';
import MainLayout from '../layout/MainLayout'; // ✅ 추가

const LecturePage = () => {
  const [lectures, setLectures] = useState([]);
  const [selectedLecture, setSelectedLecture] = useState(null);
  const [isFormOpen, setIsFormOpen] = useState(false);

  const sessionUser = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const sStudentInfo = JSON.parse(sessionStorage.getItem("sStudentInfo"));
  const studentId = sStudentInfo?.studentId;

  useEffect(() => {
    loadLectures();
  }, []);

  const loadLectures = async () => {
    try {
      const res = await fetchLectures();
      setLectures(res);
    } catch (err) {
      console.error('🚨 강의 불러오기 실패:', err);
    }
  };

  const handleAddClick = () => {
    setSelectedLecture(null);
    setIsFormOpen(true);
  };

  const handleEditClick = (lecture) => {
    setSelectedLecture(lecture);
    setIsFormOpen(true);
  };

  const handleDeleteClick = async (id) => {
    if (window.confirm('정말 삭제할까요?')) {
      try {
        await deleteLecture(id);
        loadLectures();
      } catch (error) {
        console.error('삭제 실패:', error);
        alert('삭제 중 오류가 발생했습니다.');
      }
    }
  };

  const handleEnroll = async (lectureId) => {
    if (!studentId) {
      alert('학생 정보가 없습니다. 다시 로그인 해주세요.');
      return;
    }

    try {
      const enrollmentData = {
        lectureId,
        studentId,
      };
      await createEnrollment(enrollmentData);
      alert('수강신청 완료! 🎉');
    } catch (error) {
      console.error('❌ 수강신청 실패:', error);
      alert('수강신청 중 오류가 발생했습니다.');
    }
  };

  const handleFormSubmit = async (data) => {
    try {
      if (selectedLecture) {
        await updateLecture(selectedLecture.lectureId, data);
      } else {
        await createLecture(data);
      }
      setIsFormOpen(false);
      loadLectures();
    } catch (error) {
      console.error('저장 실패:', error);
      alert('저장 중 오류가 발생했습니다.');
    }
  };

  return (
    <MainLayout>
      <div>
        {/* 상단 강의 링크 */}
        <nav className="lecture-nav">
          <Link to="/lectures">강의 관리</Link>
          <Link to="/enrollments">수강신청 (새로운 강의)</Link>
          <Link to="/my-enrollments">내 수강신청 목록</Link>
          <Link to="/subjects">과목 관리</Link>
          <Link to="/classrooms">강의실 관리</Link>
          <Link to="/schedules">시간표 관리</Link>
        </nav>

        <h1>강의 관리</h1>
        {(sessionUser?.role === 'PROFESSOR' || sessionUser?.role === 'ADMIN') && (
          <button onClick={handleAddClick}>강의 추가</button>
        )}

        <LectureList
          lectures={lectures}
          onEdit={handleEditClick}
          onDelete={handleDeleteClick}
          onEnroll={handleEnroll}
        />

        {isFormOpen && (
          <LectureForm
            lecture={selectedLecture}
            onSubmit={handleFormSubmit}
            onCancel={() => setIsFormOpen(false)}
          />
        )}
      </div>
    </MainLayout>
  );
};

export default LecturePage;