import React, { useEffect, useState } from 'react';
import { fetchEnrollmentsByStudentId } from '../api/enrollmentApi';
import { Link } from "react-router-dom";
import MainLayout from '../layout/MainLayout'; // ✅ 추가
import '../css/LectureHeader.css';

const MyEnrollmentsPage = () => {
  // ✅ 세션에서 studentId(userId) 꺼내기
  const sessionStudent = JSON.parse(sessionStorage.getItem("sStudentInfo"));
  const actualUserId = sessionStudent?.userId || null;

  console.log("MyEnrollmentsPage 렌더링됨, actualUserId:", actualUserId);

  const [enrollments, setEnrollments] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log("useEffect 실행됨, actualUserId:", actualUserId);

    if (!actualUserId) {
      console.log("userId가 없어서 API 호출 안 함");
      setEnrollments([]);
      setLoading(false);
      return;
    }

    const loadEnrollments = async () => {
      setLoading(true);
      setError(null);
      try {
        console.log(`사용자 ID ${actualUserId}의 수강신청 목록 불러오는 중...`);
        const response = await fetchEnrollmentsByStudentId(actualUserId);
        console.log('API 응답 데이터:', response);
        setEnrollments(response.data || []);
      } catch (error) {
        console.error('수강신청 목록 불러오기 실패:', error);
        setError('데이터를 불러오는데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    loadEnrollments();
  }, [actualUserId]);

  console.log("렌더링 전 상태 - enrollments:", enrollments, "loading:", loading, "error:", error);

  if (loading) return <div>로딩 중...</div>;
  if (error) return <div>에러 발생: {error}</div>;

  return (
    <MainLayout>
      <div style={{ padding: '20px' }}>
        {/* 상단 강의 링크 */}
        <nav className="lecture-nav">
          <Link to="/lectures">강의 관리</Link>
          <Link to="/enrollments">수강신청 (새로운 강의)</Link>
          <Link to="/my-enrollments">내 수강신청 목록</Link>
          <Link to="/subjects">과목 관리</Link>
          <Link to="/classrooms">강의실 관리</Link>
          <Link to="/schedules">시간표 관리</Link>
        </nav>

        <h2 style={{ color: '#003366', textAlign: 'center', margin: '20px 0' }}>내 수강신청 목록</h2>

        {enrollments.length === 0 ? (
          <p style={{ textAlign: 'center' }}>수강신청한 강의가 없습니다.</p>
        ) : (
          <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
            <thead>
              <tr style={{ backgroundColor: '#cfe2ff' }}>
                <th style={{ padding: '10px' }}>강의명</th>
                <th style={{ padding: '10px' }}>교수명</th>
                <th style={{ padding: '10px' }}>강의실</th>
                <th style={{ padding: '10px' }}>학점</th>
                <th style={{ padding: '10px' }}>신청일</th>
              </tr>
            </thead>
            <tbody>
              {enrollments.map((enroll) => (
                <tr key={enroll.enrollmentId} style={{ borderBottom: '1px solid #dee2e6' }}>
                  <td style={{ padding: '10px' }}>{enroll.lecture?.subjectName || '정보 없음'}</td>
                  <td style={{ padding: '10px' }}>{enroll.lecture?.professorName || '정보 없음'}</td>
                  <td style={{ padding: '10px' }}>{enroll.lecture?.classroomName || '정보 없음'}</td>
                  <td style={{ padding: '10px' }}>{enroll.lecture?.credit || '-'}</td>
                  <td style={{ padding: '10px' }}>{enroll.enrollmentDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </MainLayout>
  );
};

export default MyEnrollmentsPage;