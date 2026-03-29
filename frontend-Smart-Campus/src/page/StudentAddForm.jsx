import React, { useState } from 'react';
import { studentCreate } from '../api/studentsApi';

function StudentAddForm() {
  const [formData, setFormData] = useState({
    studentId: '',
    studentMajor: '',
    studentGrade: '',
    studentPhone: '',
    studentStatus: ''
  });

  const handleChange = (e) => {
    setFormData({...formData, [e.target.name]: e.target.value});
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await studentCreate(formData);
      alert('학생이 등록되었습니다!');
    
      navigate('/student-list');
      
    } catch (error) {
      console.error('학생 등록 실패:', error);
      alert('등록 실패!');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input name="studentId" value={formData.studentId} onChange={handleChange} placeholder="학번" />
      <input name="studentMajor" value={formData.studentMajor} onChange={handleChange} placeholder="전공" />
      <input name="studentGrade" value={formData.studentGrade} onChange={handleChange} placeholder="학년" />
      <input name="studentPhone" value={formData.studentPhone} onChange={handleChange} placeholder="전화번호" />
      <input name="studentStatus" value={formData.studentStatus} onChange={handleChange} placeholder="상태" />
      <button type="submit">등록</button>
    </form>
  );
}

export default StudentAddForm;
