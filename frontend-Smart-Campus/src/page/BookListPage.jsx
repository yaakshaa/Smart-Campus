import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { getBooksByKeyword } from '../api/bookApi';
import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/BookListPage.css';

function BookListPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const keyword = searchParams.get('keyword') || '';

  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);

  const itemsPerPage = 10;
  const totalPages = Math.ceil(books.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const pageBooks = books.slice(startIndex, startIndex + itemsPerPage);

  useEffect(function () {
    if (!keyword.trim()) {
      alert('검색어가 없습니다.');
      navigate(-1);
      return;
    }
    getBooksByKeyword(keyword.trim())
      .then(function (list) {
        setBooks(Array.isArray(list) ? list : []);
      })
      .catch(function (err) {
        console.error(err);
        setBooks([]);
      })
      .finally(function () {
        setLoading(false);
      });
  }, [keyword, navigate]);

  if (loading) {
    return <p style={{ textAlign: 'center' }}>로딩 중…</p>;
  }

  if (books.length === 0) {
    return (
      <>
        <Header />
        <div className="booklist-container">
          <Sidebar />
          <main className="booklist-main no-results">
            <h2>🔍 “{keyword}” 검색 결과가 없습니다.</h2>
            <div className="booklist-buttons">
              <button
                className="btn-primary"
                onClick={function () { navigate(-1); }}
              >
                도서관 메인페이지
              </button>
              <button
                className="btn-primary"
                onClick={function () { navigate('/library/books/bookLists'); }}
              >
                전체 도서 목록페이지
              </button>
            </div>
          </main>
        </div>
        <Footer />
      </>
    );
  }

  function prevPage() {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  }
  function nextPage() {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  }

  return (
    <>
      <Header />
      <div className="booklist-container">
        <Sidebar />
        <main className="booklist-main">
          <h2>“{keyword}” 검색 결과</h2>

          <table className="book-table">
            <thead>
              <tr>
                {/* ① 책 이미지 전용 컬럼 */}
                <th className="col-image"></th>
                <th className="col-title">제목</th>
                <th className="col-author">저자</th>
                <th className="col-publisher">출판사</th>
                <th className="col-status">대출상태</th>
              </tr>
            </thead>
            <tbody>
              {pageBooks.map(function (book) {
                return (
                  <tr key={book.bookId}>
                    {/* ② 이미지 셀: 이미지만 출력 */}
                    <td className="col-image">
                      <img
                        className="book-cover-small"
                        src={book.bookImg}
                        alt={book.title}
                      />
                    </td>
                    <td className="col-title">
                      {/* 제목 클릭 시 디테일 페이지로 */}
                      <Link
                        to={`/library/books/${book.bookId}`}
                        className="title-text"
                      >
                        {book.title}
                      </Link>
                    </td>
                    <td className="col-author">{book.author}</td>
                    <td className="col-publisher">{book.publisher}</td>
                    <td className="col-status">
                      {book.bookStatus}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>

          {/* 페이지네비게이션 */}
          <div className="pagination-bookList">
            <button
              className="pagination-button-bookList"
              onClick={prevPage}
              disabled={currentPage === 1}
            >
              이전
            </button>
            {Array.from({ length: totalPages }).map(function (_, i) {
              const pageNum = i + 1;
              return (
                <button
                  key={pageNum}
                  className={
                    'pagination-button-bookList' +
                    (pageNum === currentPage ? ' active' : '')
                  }
                  onClick={function () { setCurrentPage(pageNum); }}
                >
                  {pageNum}
                </button>
              );
            })}
            <button
              className="pagination-button-bookList"
              onClick={nextPage}
              disabled={currentPage === totalPages}
            >
              다음
            </button>
          </div>
        </main>
      </div>
      <Footer />
    </>
  );
}

export default BookListPage;