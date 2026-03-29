import { campusApi } from './campusApi';

export const fetchEnrollments = () => campusApi.get('/api/enrollments');
export const fetchEnrollmentById = (id) => campusApi.get(`/api/enrollments/${id}`);
export const createEnrollment = (data) => campusApi.post('/api/enrollments', data);
export const updateEnrollment = (id, data) => campusApi.put(`/api/enrollments/${id}`, data);
export const deleteEnrollment = (id) => campusApi.delete(`/api/enrollments/${id}`);

// 내 수강신청 목록 조회
export const fetchEnrollmentsByStudentId = (studentId) => 
  campusApi.get(`/api/enrollments/student/${studentId}`);
