import React, { useEffect, useState, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getNoticeById, deleteNotice } from "../api/noticeApi";
import { UserContext } from "../App";

import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/MainLayout.css';
import '../css/NoticeDetail.css';

function NoticeDetailPage() {
  const { noticeNo } = useParams();
  const navigate = useNavigate();
  const { loginStatus } = useContext(UserContext);
  const [notice, setNotice] = useState(null);

  useEffect(() => {
    const fetchNotice = async () => {
      try {
        const response = await getNoticeById(noticeNo);
        setNotice(response.data.data);
      } catch (error) {
        console.error("공지사항 불러오기 실패", error);
      }
    };
    fetchNotice();
  }, [noticeNo]);

  const handleDelete = async () => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;

    try {
      await deleteNotice(noticeNo);
      alert("삭제되었습니다.");
      navigate("/notices");
    } catch (error) {
      console.error("삭제 실패", error);
      alert("삭제에 실패했습니다.");
    }
  };

  if (!notice) return <div>로딩 중...</div>;

  const isAdmin = loginStatus.loginUser?.role === "ADMIN";

  return (
    <div  className="main-layout">
      <Header />
      <div className="body-layout">
        <Sidebar />
        <main className="main-content">
          <div className="notice-detail-wrapper">
      <h2 className="notice-detail-title">{notice.noticeTitle}</h2>
      <p className="notice-detail-meta">작성일: {notice.noticeCreatedAt?.slice(0, 10)}</p>
      <hr />
      <div className="notice-detail-content">
        <p>{notice.noticeContent}</p>
      </div>

      {notice.files && notice.files.length > 0 && (
        <div className="notice-detail-attachments">
          <h4>첨부파일</h4>
          <ul>
            {notice.files.map((file, index) => (
              <li key={index}>
                <a href={file.filePath} download>
                  {file.fileName}
                </a>
              </li>
            ))}
          </ul>
        </div>
      )}

    <div className="notice-detail-buttons">
      {isAdmin && (
        <>
          <button onClick={() => navigate(`/notices/edit/${noticeNo}`)}>수정</button>
          <button onClick={handleDelete}>삭제</button>
        </>
      )}

      <button onClick={() => navigate("/notices")}>목록으로</button>
      </div>
      </div>
      </main>
      </div>
      <Footer />
    </div>
  );
}

export default NoticeDetailPage;
