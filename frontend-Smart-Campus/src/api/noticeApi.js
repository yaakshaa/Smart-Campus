import axios from 'axios';

const BACKEND_SERVER = 'http://localhost:8080';

// 공지사항 전체 조회
export const getNoticeList = async () => {
  return await axios.get(`${BACKEND_SERVER}/notices`, {
    withCredentials: true,
  });
};

// 공지사항 상세 조회
export const getNoticeById = async (noticeNo) => {
  return await axios.get(`${BACKEND_SERVER}/notices/${noticeNo}`);
};

// 공지사항 등록 (관리자 전용)
export const createNotice = async (noticeRequestDTO, files) => {
  const formData = new FormData();
  formData.append(
    'requestDTO',
    new Blob([JSON.stringify(noticeRequestDTO)], { type: 'application/json' })
  );
  if (files) {
    for (let file of files) {
      formData.append('files', file); // 백엔드 파라미터명 'files'와 일치
    }
  }

  return await axios.post(`${BACKEND_SERVER}/notices/admin`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    withCredentials: true,
  });
};

// 공지사항 수정 (관리자 전용)
export const updateNotice = async (noticeNo, noticeRequestDTO, files) => {
  const formData = new FormData();
  formData.append(
    'requestDTO',
    new Blob([JSON.stringify(noticeRequestDTO)], { type: 'application/json' })
  );
  if (files) {
    for (let file of files) {
      formData.append('addFiles', file); // 백엔드 파라미터명 'addFiles'와 일치
    }
  }

  return await axios.put(`${BACKEND_SERVER}/notices/admin/${noticeNo}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    withCredentials: true,
  });
};

// 공지사항 삭제 (관리자 전용)
export const deleteNotice = async (noticeNo) => {
  return await axios.delete(`${BACKEND_SERVER}/notices/admin/${noticeNo}`, {
    withCredentials: true,
  });
};
