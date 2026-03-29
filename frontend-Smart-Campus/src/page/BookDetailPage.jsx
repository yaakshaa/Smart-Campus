import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  getBookById,
  getBooksByCategory,
  borrowBook,
  returnBook
} from '../api/bookApi';
import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/BookDetailPage.css';

export default function BookDetailPage() {
  const { bookId } = useParams();
  const navigate = useNavigate();

  // ① 훅은 최상단에
  const [book, setBook] = useState(null);
  const [related, setRelated] = useState([]);    // 같은 카테고리 도서 목록
  const [loading, setLoading] = useState(true);
  const [actioning, setActioning] = useState(false);

  const [canReturn, setCanReturn] = useState(false);   // 내가 빌렸는지 여부
  const [borrowId, setBorrowId] = useState(null);    // 반납용 PK

  const isAvailable = book?.bookStatus === '대출가능';

  // ② 상세정보 + 관련 도서 동시에 불러오기
  useEffect(() => {
    async function fetchData() {
      try {
        // 상세
        const res = await getBookById(bookId);
        const dto = res.data?.data || res.data;
        
        setBook(dto.book);
        setCanReturn(dto.canReturn);
        setBorrowId(dto.borrowId);

        // 같은 카테고리 (자기 자신 제외)
        const list = await getBooksByCategory(dto.book.category);
        setRelated(list.filter(b => b.bookId !== dto.book.bookId));
      } catch {
        alert('도서 정보를 불러오지 못했습니다.');
        navigate(-1);
      } finally {
        setLoading(false);
      }
    }
    fetchData();
  }, [bookId, navigate]);

  // ③ 로딩 / 존재 체크
  if (loading) {
    return <p className="detail-loading">로딩 중…</p>;
  }
  if (!book) {
    return <p className="detail-loading">존재하지 않는 도서입니다.</p>;
  }

  // ④ 대출 / 반납 핸들러
  const handleBorrow = async () => {
    if (actioning) return;
    setActioning(true);
    try {
      await borrowBook(book.bookId);
      alert('대출이 완료되었습니다!');
      const upd = (await getBookById(bookId)).data.data;
      setBook(upd.book);
      setCanReturn(upd.canReturn);
      setBorrowId(upd.borrowId);
    } catch (e) {
      alert(e.response?.data?.message || '대출 중 오류가 발생했습니다.');
    } finally {
      setActioning(false);
    }
  };

  const handleReturn = async () => {
    if (actioning) return;
    setActioning(true);
    try {
      await returnBook(borrowId);
      alert('반납이 완료되었습니다!');
      const upd = (await getBookById(bookId)).data.data;
      setBook(upd.book);
      setCanReturn(upd.canReturn);
      setBorrowId(upd.borrowId);
    } catch (e) {
      console.error(e.response);
      alert(e.response?.data?.message || '반납 중 오류가 발생했습니다.');
    } finally {
      setActioning(false);
    }
  };

  // ⑤ 렌더링
  return (
    <>
      <Header />
      <div className="BookDetailPage detail-container">
        <Sidebar />
        <main className="detail-main">

          {/* ── 상세 카드 ── */}
          <div className="detail-card">
            <div className="detail-cover-wrap">
              <img
                className="detail-cover"
                src={book.bookImg}
                alt={book.title}
              />
            </div>
            <div className="detail-info">
              <h1 className="detail-title">{book.title}</h1>
              <p className={`detail-status ${isAvailable ? 'available' : 'borrowed'}`}>
                {book.bookStatus}
              </p>
              <ul className="detail-meta">
                <li><strong>저자</strong> {book.author}</li>
                <li><strong>출판사</strong> {book.publisher} ({book.publisherYear})</li>
                <li><strong>ISBN</strong> {book.isbn}</li>
                <li><strong>카테고리</strong> {book.category}</li>
                <li><strong>재고</strong> {book.stock}권</li>
              </ul>
              {isAvailable ? (
              <button
                type="button"
                className="detail-action-btn btn-borrow"
                onClick={handleBorrow}
                disabled={actioning}
              >
                대출하기
              </button>
            ) : canReturn ? ( 
               <button
                  type="button"
                  className="detail-action-btn btn-return"
                  onClick={handleReturn}
                  disabled={actioning}
                >
                  반납하기
                </button>
              ) : (
                <span className="detail-unavailable"></span>
              )} 
            </div>
          </div>

          {/* ── 같은 카테고리 도서 ── */}
          {related.length > 0 && (
            <section className="detail-related-section">
              <h2>관련 도서</h2>
              <div className="detail-related-list">
                {related.map(b => (
                  <div
                    key={b.bookId}
                    className="detail-related-item"
                    onClick={() => navigate(`/library/books/${b.bookId}`)}
                  >
                    <img src={b.bookImg} alt={b.title} />
                    <p>{b.title}</p>
                    <p>{b.author}</p>
                  </div>
                ))}
              </div>
            </section>
          )}

        </main>
      </div>
      <Footer />
    </>
  );
}