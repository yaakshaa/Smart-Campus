import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getNoticeById, updateNotice } from '../api/noticeApi';
import { UserContext } from '../App';

import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/MainLayout.css';
import '../css/NoticeCreateEdit.css';

const NoticeEditPage = () => {
  const { noticeNo } = useParams();
  const navigate = useNavigate();
  const { loginStatus } = useContext(UserContext);

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [files, setFiles] = useState([]);

  // 관리자 아닌 경우 접근 제한
  useEffect(() => {
    const isAdmin = loginStatus.loginUser?.role === 'ADMIN';
    if (!loginStatus.isLogin || !isAdmin) {
      alert('접근 권한이 없습니다.');
      navigate('/notices');
    }
  }, [loginStatus, navigate]);

  // 기존 공지사항 정보 불러오기
  useEffect(() => {
    const fetchNotice = async () => {
      try {
        const response = await getNoticeById(noticeNo);
        const notice = response.data.data;
        setTitle(notice.noticeTitle);
        setContent(notice.noticeContent);
      } catch (error) {
        console.error('공지사항 불러오기 실패', error);
        alert('공지사항을 불러오지 못했습니다.');
        navigate('/notices');
      }
    };
    fetchNotice();
  }, [noticeNo, navigate]);

  // 파일 선택 처리 함수
  const handleFileChange = (e) => {
    setFiles(Array.from(e.target.files));
  };

  // 공지사항 수정
  const handleSubmit = async (e) => {
    e.preventDefault();

    const noticeRequestDTO = {
      noticeTitle: title,
      noticeContent: content,
    };

    try {
      await updateNotice(noticeNo, noticeRequestDTO, files);
      alert('수정되었습니다.');
      navigate(`/notices/${noticeNo}`);
    } catch (error) {
      console.error('공지사항 수정 실패', error);
      alert('수정에 실패했습니다.');
    }
  };

  return (
    <div className="main-layout">
      <Header />
      <div className="body-layout">
        <Sidebar />
        <main className="main-content">
          <div className="notice-form-container">
      <h2 className="notice-title">공지사항 수정</h2>
      <form className="notice-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>내용</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>첨부파일</label>
          <input
            type="file"
            multiple
            onChange={handleFileChange} 
          />
        </div>
        <div className="form-button-group">
        <button type="submit" className="btn-submit">수정 완료</button>
        <button type="button" className="btn-cancel" onClick={() => navigate(`/notices/${noticeNo}`)}>
          취소
        </button>
        </div>
      </form>
      </div>
      </main>
      </div>
      <Footer />
    </div>
  );
};

export default NoticeEditPage;
