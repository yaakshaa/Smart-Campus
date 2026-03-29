import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/grade-info';

/**
 * 전체 성적 조회
 * @param {number} studentId
 * @returns {Promise}
 */
export const fetchAllGrades = async (studentId) => {
  const response = await axios.get(`${BASE_URL}?studentId=${studentId}`);
  return response.data;
};

/**
 * 특정 학기의 성적만 조회
 * @param {number} studentId
 * @param {number} termId
 * @returns {Promise}
 */
export const fetchGradesByTerm = async (studentId, termId) => {
  const response = await axios.get(`${BASE_URL}/filter`, {
    params: { studentId, termId },
  });
  return response.data;
};

/**
 * 성적 요약 정보 조회
 * @param {number} studentId
 * @param {number} termId
 * @returns {Promise}
 */
export const fetchTermSummary = async (studentId, termId) => {
  const response = await axios.get(`${BASE_URL}/summary`, {
    params: { studentId, termId },
  });
  return response.data;
};
