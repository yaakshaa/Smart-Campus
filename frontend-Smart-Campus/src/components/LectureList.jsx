import React from 'react';

const LectureList = ({ lectures = [], onEdit, onDelete, onEnroll }) => {
  console.log('📋 LectureList 렌더링됨');
  console.log('✅ 전달된 lectures:', lectures);

  // ✅ 로그인한 사용자 정보 가져오기
  const userInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const userRole = userInfo?.role;

  const isStudent = userRole === 'STUDENT';

  return (
    <table border="1" cellPadding="5" style={{ width: '100%', marginTop: '20px' }}>
      <thead>
        <tr>
          <th>ID</th>
          <th>과목명</th>
          <th>교수ID</th>
          <th>강의실ID</th>
          <th>최대인원</th>
          <th>액션</th> {/* 항상 렌더링 */}
          <th>수강신청</th>
        </tr>
      </thead>
      <tbody>
        {lectures.map((lec) => (
          <tr key={lec.lectureId}>
            <td>{lec.lectureId}</td>
            <td>{lec.subjectName || lec.subjectNo}</td>
            <td>{lec.professorId}</td>
            <td>{lec.classroomId}</td>
            <td>{lec.maxEnrollment}</td>
            <td>
              {/* 버튼만 조건부 렌더링 */}
              {!isStudent && (
                <>
                  <button onClick={() => onEdit(lec)}>수정</button>
                  <button onClick={() => onDelete(lec.lectureId)}>삭제</button>
                </>
              )}
            </td>
            <td>
              <button onClick={() => onEnroll(lec.lectureId)}>수강신청</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};


export default LectureList;