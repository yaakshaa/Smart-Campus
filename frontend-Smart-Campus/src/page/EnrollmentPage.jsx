import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { fetchLectures } from '../api/lectureApi';
import { createEnrollment } from '../api/enrollmentApi';
import MainLayout from '../layout/MainLayout'; // ✅ 추가
import '../css/EnrollmentPage.css';
import '../css/LectureHeader.css';

const EnrollmentPage = () => {
  const [lectures, setLectures] = useState([]);
  const [selectedLectureIds, setSelectedLectureIds] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null); // 에러 상태 관리 추가

  useEffect(() => {
    loadLectures();
  }, []);

  const loadLectures = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await fetchLectures();
      console.log('API 응답 데이터 (전체):', res); // 실제 응답 형태 확인용 로그

      // 응답이 Axios 객체 형태이고 res.data가 배열일 경우
      if (res && res.data && Array.isArray(res.data)) {
        console.log('res.data가 배열임, 길이:', res.data.length);
        setLectures(res.data);
      }
      // 응답 자체가 바로 배열일 경우 (Axios가 data를 직접 반환할 때)
      else if (Array.isArray(res)) {
        console.log('res 자체가 배열임, 길이:', res.length);
        setLectures(res);
      }
      else {
        console.log('API 응답 데이터가 예상 형식이 아님:', res);
        setLectures([]); // 예상치 못한 형태면 빈 배열
      }
    } catch (err) {
      console.error('강의 목록 불러오기 실패:', err);
      setError('강의 목록을 불러오는 데 실패했습니다.');
      setLectures([]); // 에러 발생 시 빈 배열
    } finally {
      setLoading(false);
    }
  };

  const handleCheckboxChange = (lectureId) => {
    setSelectedLectureIds((prev) =>
      prev.includes(lectureId)
        ? prev.filter((id) => id !== lectureId)
        : [...prev, lectureId]
    );
  };

  const handleEnrollAll = async () => {
    if (selectedLectureIds.length === 0) {
      alert('수강신청할 강의를 선택해주세요!');
      return;
    }

    const sStudentInfo = sessionStorage.getItem("sStudentInfo");
    if (!sStudentInfo) {
      alert("학생 로그인 정보가 없습니다. 다시 로그인 해주세요.");
      return;
    }

    const parsedStudent = JSON.parse(sStudentInfo);
    const studentId = parsedStudent.studentId; // ✅ 실제 studentId (Long 타입)

    setLoading(true);
    try {
      for (const lectureId of selectedLectureIds) {
        const numericLectureId = Number(lectureId);
        console.log(`수강신청 데이터:`, { lectureId: numericLectureId, studentId });
        await createEnrollment({ lectureId: numericLectureId, studentId }); // ✅ 정확한 값 전송
      }
      alert('수강신청 완료! 🎉');
      setSelectedLectureIds([]);
    } catch (err) {
      console.error('수강신청 실패:', err);
      alert('수강신청 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <p style={{ textAlign: 'center', marginTop: '50px' }}>강의 목록 로딩 중...</p>;
  }

  if (error) {
    return <p style={{ textAlign: 'center', marginTop: '50px', color: 'red' }}>{error}</p>;
  }

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


        <h2>수강신청 가능한 강의 목록</h2>
        <table border="1" cellPadding="5" style={{ width: '100%' }}>
          <thead>
            <tr>
              <th>선택</th>
              <th>과목명</th>
              <th>교수명</th>
              <th>학점</th>
              <th>수강인원/제한</th>
              <th>강의실</th>
              <th>비고</th>
            </tr>
          </thead>
          <tbody>
            {lectures.length === 0 ? (
              <tr>
                <td colSpan="7" style={{ textAlign: 'center' }}>수강신청 가능한 강의가 없습니다.</td>
              </tr>
            ) : (
              lectures.map((lec) => (
                <tr key={lec.lectureId}>
                  <td>
                    <input
                      type="checkbox"
                      checked={selectedLectureIds.includes(lec.lectureId)}
                      onChange={() => handleCheckboxChange(lec.lectureId)}
                    />
                  </td>
                  {/* 데이터가 비어있을 경우를 대비한 안전 장치 || '' */}
                  <td>{lec.subjectName || lec.subjectNo || ''}</td>
                  <td>{lec.professorName || lec.professorId || ''}</td>
                  <td>{lec.credit || ''}</td>
                  <td>0 / {lec.maxEnrollment || ''}</td>
                  <td>{lec.classroomName || lec.classroomId || ''}</td>
                  <td>{lec.note || ''}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        <button
          onClick={handleEnrollAll}
          disabled={loading}
          style={{
            marginTop: '20px',
            padding: '10px 20px',
            backgroundColor: '#4CAF50',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          {loading ? '신청 중...' : '수강신청'}
        </button>
      </div>
    </MainLayout>
  );
};

export default EnrollmentPage;