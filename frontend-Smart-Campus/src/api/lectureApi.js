
import { getCampus as get, postCampus as post, put, del } from './campusApi';

const LECTURE_API_BASE = '/api/lectures';

export const fetchLectures = async () => {
    const res = await get(LECTURE_API_BASE);
    console.log('[fetchLectures 응답]:', res); // ✅ 로그로 확인
    return res;
};
export const fetchLectureById = (id) => get(`${LECTURE_API_BASE}/${id}`);
export const createLecture = (data) => post(LECTURE_API_BASE, data);
export const updateLecture = (id, data) => put(`${LECTURE_API_BASE}/${id}`, data);
export const deleteLecture = (id) => del(`${LECTURE_API_BASE}/${id}`);
