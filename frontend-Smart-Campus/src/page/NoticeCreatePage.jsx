import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { createNotice } from '../api/noticeApi';
import { UserContext } from '../App';

import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/MainLayout.css';
import '../css/NoticeCreateEdit.css';

function NoticeCreatePage() {
  const { loginStatus } = useContext(UserContext);
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [files, setFiles] = useState([]);

  const isAdmin = loginStatus.loginUser?.role === 'ADMIN';
  
  if (!isAdmin) {
    alert('접근 권한이 없습니다.');
    navigate('/notices');
    return null;
  }

  const handleFileChange = (e) => {
    setFiles(e.target.files);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const noticeRequestDTO = {
      noticeTitle: title,
      noticeContent: content,
    };

    try {
      await createNotice(noticeRequestDTO, files);
      alert('공지사항이 등록되었습니다.');
      navigate('/notices');
    } catch (error) {
      console.error('공지사항 등록 실패', error);
      alert('등록에 실패했습니다.');
    }
  };


  return (
    <div className="main-layout">
      <Header />
      <div className="body-layout">
        <Sidebar />
        <main className="main-content">
          <div className="notice-form-container">
      <h2 className="notice-title">공지사항 등록</h2>
      <form className="notice-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>제목: </label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>내용: </label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            rows={10}
            required
          />
        </div>
        <div className="form-group">
          <label>첨부파일: </label>
          <input type="file" multiple onChange={handleFileChange} />
        </div>
        <div className="form-button-group">
        <button type="submit" className="btn-submit">등록</button>
        <button type="button"  className="btn-cancel" onClick={() => navigate('/notices')}>취소</button>
        </div>
      </form>
      </div>
      </main>
      </div>
      <Footer />
    </div>
  );
}

export default NoticeCreatePage;
