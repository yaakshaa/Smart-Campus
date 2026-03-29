import React, { useEffect, useState, useContext } from 'react';
import { getNoticeList } from '../api/noticeApi';
import { useNavigate } from 'react-router-dom';
import { UserContext } from "../App";

import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/MainLayout.css';
import '../css/Notice.css';

const NoticeListPage = () => {
  const [notices, setNotices] = useState([]);
  const navigate = useNavigate();

  const { loginStatus } = useContext(UserContext); 
  const isAdmin = loginStatus.loginUser?.role === "ADMIN"; 

  useEffect(() => {
    const fetchNotices = async () => {
      try {
        const response = await getNoticeList();
        setNotices(response.data.data); // 백엔드 응답의 data 배열 저장
      } catch (error) {
        console.error('공지사항 목록 조회 실패:', error);
      }
    };

    fetchNotices();
  }, []);

  const handleClick = (noticeNo) => {
    navigate(`/notices/${noticeNo}`);
  };

  return (
    <div className="main-layout">
      <Header />
      <div className="body-layout">
        <Sidebar />
        <main className="main-content">
      <h2 className="notice-title">공지사항</h2>

      {isAdmin && (
        <div className="notice-create-button-wrapper">
        <button className="notice-create-button" onClick={() => navigate("/notices/create")}>
          공지 등록
        </button>
        </div>
      )}

      <table className="notice-table">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회수</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(notices) && notices.map((notice) => (
            <tr key={notice.noticeNo} onClick={() => handleClick(notice.noticeNo)}>
              <td>{notice.noticeNo}</td>
              <td>{notice.noticeTitle}</td>
              <td>{notice.adminId}</td>
              <td>{notice.noticeCreatedAt?.slice(0, 10)}</td>
              <td>{notice.noticeCount}</td>
            </tr>
          ))}
        </tbody>
      </table>
      </main>
      </div>
      <Footer />
    </div>
  );
};

export default NoticeListPage;
