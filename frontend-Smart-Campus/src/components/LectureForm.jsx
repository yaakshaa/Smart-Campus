import React, { useState, useEffect } from 'react';

const LectureForm = ({ lecture, onSubmit, onCancel }) => {
  // `lecture` prop이 있으면 수정 모드, 없으면 생성 모드라고 판단
  const isEditing = !!lecture; // lecture 객체가 존재하면 true, 아니면 false

  const [formData, setFormData] = useState({
    // 생성 모드일 때는 lectureId를 초기화하지 않거나 빈 문자열로,
    // 수정 모드일 때는 기존 lecture.lectureId로 초기화
    lectureId: isEditing ? (lecture.lectureId || '') : '',
    professorId: isEditing ? (lecture.professorId || '') : '',
    subjectNo: isEditing ? (lecture.subjectNo || '') : '',
    termId: isEditing ? (lecture.termId || '') : '',
    classroomId: isEditing ? (lecture.classroomId || '') : '',
    maxEnrollment: isEditing ? (lecture.maxEnrollment || '') : '',
  });

  useEffect(() => {
    if (isEditing) { // 수정 모드일 때만 기존 데이터로 폼 채우기
      setFormData({
        lectureId: lecture.lectureId || '',
        professorId: lecture.professorId || '',
        subjectNo: lecture.subjectNo || '',
        termId: lecture.termId || '',
        classroomId: lecture.classroomId || '',
        maxEnrollment: lecture.maxEnrollment || '',
      });
    } else { // 생성 모드일 때 폼 초기화 (만약을 대비해)
        setFormData({
            lectureId: '', // 생성 모드에서 ID는 보내지 않을 것이므로 빈 값
            professorId: '',
            subjectNo: '',
            termId: '',
            classroomId: '',
            maxEnrollment: '',
        });
    }
  }, [lecture, isEditing]); // lecture 또는 isEditing이 변경될 때 폼 초기화/업데이트

  const handleChange = (e) => {
    const { name, value } = e.target;
    // 숫자 필드는 숫자로 변환 (e.g., professorId, subjectNo, etc.)
    const newValue = ['professorId', 'subjectNo', 'termId', 'classroomId', 'maxEnrollment'].includes(name)
                       ? Number(value) : value; // 숫자 아닌 값은 그대로
    setFormData((prev) => ({ ...prev, [name]: newValue }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 생성 모드일 때는 lectureId를 포함시키지 않음
    const dataToSend = isEditing ? formData : {
        professorId: formData.professorId,
        subjectNo: formData.subjectNo,
        termId: formData.termId,
        classroomId: formData.classroomId,
        maxEnrollment: formData.maxEnrollment,
        // lectureId는 자동 생성되므로 보내지 않음
    };
    onSubmit(dataToSend);
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginTop: '20px' }}>
      {isEditing && ( // 수정 모드일 때만 강의 ID 필드 보여주기
        <div>
          <label>강의 ID: </label>
          <input 
            name="lectureId" 
            value={formData.lectureId} 
            readOnly // 수정 불가능하게 읽기 전용으로 설정
            style={{ backgroundColor: '#f0f0f0' }} // 읽기 전용임을 시각적으로 표현
          />
        </div>
      )}
      <div>
        <label>교수 ID: </label>
        <input 
          name="professorId" 
          type="number" // 숫자 입력 유도
          value={formData.professorId} 
          onChange={handleChange} 
          required 
        />
      </div>
      <div>
        <label>과목 번호: </label>
        <input 
          name="subjectNo" 
          type="number" // 숫자 입력 유도
          value={formData.subjectNo} 
          onChange={handleChange} 
          required 
        />
      </div>
      <div>
        <label>학기 ID: </label>
        <input 
          name="termId" 
          type="number" // 숫자 입력 유도
          value={formData.termId} 
          onChange={handleChange} 
          required 
        />
      </div>
      <div>
        <label>강의실 ID: </label>
        <input 
          name="classroomId" 
          type="number" // 숫자 입력 유도
          value={formData.classroomId} 
          onChange={handleChange} 
          required 
        />
      </div>
      <div>
        <label>최대 수강 인원: </label>
        <input 
          name="maxEnrollment" 
          type="number" 
          value={formData.maxEnrollment} 
          onChange={handleChange} 
          required 
        />
      </div>
      <button type="submit">저장</button>
      <button type="button" onClick={onCancel} style={{ marginLeft: '10px' }}>
        취소
      </button>
    </form>
  );
};

export default LectureForm;