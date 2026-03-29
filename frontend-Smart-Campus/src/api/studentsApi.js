import axios from "axios";

const BACKEND_SERVER = 'http://localhost:8080';
const prefix = `${BACKEND_SERVER}/students`;

//학생 리스트 가져오기
export const studentList = async () => {
    const response = await axios.get(`${prefix}`);
    return response.data;
};

//학생 상세 보기
export const studentView = async (studentId) => {
    const response = await axios.get(`${prefix}/${studentId}`);
    return response.data;
};

//학생 등록
export const studentCreate = async (sendJsonObject) => {
    const response = await axios.post(
        `${prefix}`,
        sendJsonObject,
        {
            headers: { 'Content-Type': 'application/json' }
        }
    );
    return response.data;
};

//학생 수정
export const studentUpdate = async (sendJsonObject) => {
    const response = await axios.put(
        `${prefix}`,
        sendJsonObject,
        {
            headers: { 'Content-Type': 'application/json' }
        }
    );
    return response.data;
};

//학생 삭제
export const studentDelete = async (studentId) => {
    const response = await axios.delete(`${prefix}/${studentId}`);
    return response.data;
};




/* import axios from 'axios';

const BACKEND_SERVER = 'http://localhost:8080';

export const studentList = async () => {
    const response = await axios.get(`${BACKEND_SERVER}/students`);
    return response.data;
};

export const studentCreate = async (student) => {
  const response = await axios.post(`${BACKEND_SERVER}/students`, student);
  return response.data;
};

export const studentUpdate = async (student) => {
  const response = await axios.put(`${BACKEND_SERVER}/students`, student);
  return response.data;
};

export const studentDelete = async (studentId) => {
  const response = await axios.delete(`${BACKEND_SERVER}/students/${studentId}`);
  return response.data;
};
 */