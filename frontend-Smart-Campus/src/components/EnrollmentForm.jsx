import React, { useState, useEffect } from 'react';
import { createEnrollment, updateEnrollment, fetchEnrollmentById } from '../api/enrollmentApi';
import { fetchLectures } from '../api/lectureApi';
import { useParams, useNavigate } from 'react-router-dom';

const EnrollmentForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    studentId: '',
    lectureId: '',
  });

  const [lectures, setLectures] = useState([]);

  useEffect(() => {
    fetchLectures().then((res) => setLectures(res));
    if (id) {
      fetchEnrollmentById(id).then((res) => setForm(res));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (id) {
      await updateEnrollment(id, form);
    } else {
      await createEnrollment(form);
    }
    navigate('/enrollments');
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{id ? '수강신청 수정' : '수강신청 등록'}</h2>
      <div>
        <label>학생 ID</label>
        <input name="studentId" value={form.studentId} onChange={handleChange} required />
      </div>
      <div>
        <label>강의 선택</label>
        <select name="lectureId" value={form.lectureId} onChange={handleChange} required>
          <option value="">-- 강의를 선택하세요 --</option>
          {lectures.map((lec) => (
            <option key={lec.lectureId} value={lec.lectureId}>
              {lec.lectureName}
            </option>
          ))}
        </select>
      </div>
      <button type="submit">저장</button>
    </form>
  );
};

export default EnrollmentForm;
