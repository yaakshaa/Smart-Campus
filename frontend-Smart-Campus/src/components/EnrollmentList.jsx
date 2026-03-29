import React, { useEffect, useState } from 'react';
import { fetchEnrollments, deleteEnrollment } from '../api/enrollmentApi';
import { useNavigate } from 'react-router-dom';

const EnrollmentList = () => {
  const [enrollments, setEnrollments] = useState([]);
  const navigate = useNavigate();

  const loadData = () => {
    fetchEnrollments().then((res) => setEnrollments(res.data));
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      await deleteEnrollment(id);
      loadData();
    }
  };

  return (
    <div>
      <h2>수강신청 목록</h2>
      <button onClick={() => navigate('/enrollments/new')}>수강신청 추가</button>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>학생 ID</th>
            <th>강의 ID</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          {enrollments.map((enrollment) => (
            <tr key={enrollment.enrollmentId}>
              <td>{enrollment.enrollmentId}</td>
              <td>{enrollment.studentId}</td>
              <td>{enrollment.lectureId}</td>
              <td>
                <button onClick={() => navigate(`/enrollments/edit/${enrollment.enrollmentId}`)}>수정</button>
                <button onClick={() => handleDelete(enrollment.enrollmentId)}>삭제</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default EnrollmentList;
