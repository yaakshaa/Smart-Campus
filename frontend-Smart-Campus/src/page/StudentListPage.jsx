/* import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminHeader from '../components/AdminHeader';
import AdminSidebar from '../components/AdminSidebar';
import { studentList } from '../api/studentsApi';

function StudentListPage() {
  const [students, setStudents] = useState([]);

  useEffect(() => {
   studentList()
      .then(response => {
        const studentsOnly = response.filter(student => student.userInfo && student.userInfo.userType === 'STUDENT');
        setStudents(studentsOnly);
      })
      .catch(error => {
        console.error('학생 목록 불러오기 실패:', error);
      });
  }, []);

  return (
    <div>
            <AdminHeader />
       <div style={{ display: 'flex' }}>
        <AdminSidebar />
        <main style={{ padding: '20px', flexGrow: 1 }}>

      <h2>학생 리스트</h2>

         <table border="1" cellPadding="8" style={{ width: '100%', textAlign: 'center' }}>
        <thead>
          <tr>
            <th>학번</th>
            <th>이름</th>
            <th>전공</th>
            <th>학년</th>
            <th>상태</th>
          </tr>
        </thead>
        <tbody>
          {students.map(student => (
            <tr key={student.studentId}>
              <td>{student.studentId}</td>
              <td>{student.userInfo ? student.userInfo.userName : '이름없음'}</td>
              <td>{student.studentMajor}</td>
              <td>{student.studentGrade}</td>
              <td>{student.studentStatus}</td>
            </tr>
          ))}
        </tbody>
      </table>
          </main>
          </div>
    </div>
  );
}

export default StudentListPage;
 */

import React, { useEffect, useState } from 'react';
import { studentList, studentCreate, studentUpdate, studentDelete } from '../api/studentsApi';
import AdminHeader from '../components/AdminHeader';
import AdminSidebar from '../components/AdminSidebar';

function StudentListPage() {
  const [students, setStudents] = useState([]);
  const [newStudent, setNewStudent] = useState({
    studentId: '',
    studentMajor: '',
    studentGrade: '',
    studentPhone: '',
    studentStatus: '',
  });

  useEffect(() => {
    loadStudents();
  }, []);

  const loadStudents = async () => {
    const data = await studentList();
    setStudents(data);
  };

  const handleAdd = async () => {
    await studentCreate(newStudent);
    loadStudents();
  };

  const handleUpdate = async (student) => {
    await studentUpdate(student);
    loadStudents();
  };

  const handleDelete = async (studentId) => {
    await studentDelete(studentId);
    loadStudents();
  };

  return (
    <div>
      <AdminHeader />
      <div style={{ display: 'flex' }}>
        <AdminSidebar />
        <main style={{ padding: '20px', flexGrow: 1 }}>
          <h2>학생 리스트</h2>

          {/* 추가 폼 */}
          <div>
            <input placeholder="학번" value={newStudent.studentId} onChange={e => setNewStudent({ ...newStudent, studentId: e.target.value })} />
            <input placeholder="전공" value={newStudent.studentMajor} onChange={e => setNewStudent({ ...newStudent, studentMajor: e.target.value })} />
            <input placeholder="학년" value={newStudent.studentGrade} onChange={e => setNewStudent({ ...newStudent, studentGrade: e.target.value })} />
            <input placeholder="전화번호" value={newStudent.studentPhone} onChange={e => setNewStudent({ ...newStudent, studentPhone: e.target.value })} />
            <input placeholder="상태" value={newStudent.studentStatus} onChange={e => setNewStudent({ ...newStudent, studentStatus: e.target.value })} />
            <button onClick={handleAdd}>추가</button>
          </div>

          {/* 학생 테이블 */}
          <table border="1" style={{ width: '100%', marginTop: '20px' }}>
            <thead>
              <tr>
                <th>학번</th>
                <th>전공</th>
                <th>학년</th>
                <th>전화번호</th>
                <th>상태</th>
                <th>수정/삭제</th>
              </tr>
            </thead>
            <tbody>
              {students.map((s) => (
                <tr key={s.studentId}>
                  <td>{s.studentId}</td>
                  <td>{s.studentMajor}</td>
                  <td>{s.studentGrade}</td>
                  <td>{s.studentPhone}</td>
                  <td>{s.studentStatus}</td>
                  <td>
                    <button onClick={() => {
                     const newMajor = prompt('새 전공', s.studentMajor);
                     const newGrade = prompt('새 학년', s.studentGrade);
                     const newPhone = prompt('새 전화번호', s.studentPhone);
                      const newStatus = prompt('새 상태', s.studentStatus);
                      handleUpdate({
                      ...s,
                     studentMajor: newMajor,
                     studentGrade: newGrade,
                     studentPhone: newPhone,
                     studentStatus: newStatus
    });
}}>
수정</button>
                    <button onClick={() => handleDelete(s.studentId)}>삭제</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </main>
      </div>
    </div>
  );
}

export default StudentListPage;
