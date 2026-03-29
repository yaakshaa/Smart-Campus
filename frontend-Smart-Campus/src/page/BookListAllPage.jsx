// src/page/BookListAllPage.jsx
import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate }       from 'react-router-dom';
import { getAllBooks, getBooksByCategory } from '../api/bookApi';
import Header    from '../layout/Header';
import Sidebar   from '../layout/Sidebar';
import Footer    from '../layout/Footer';
import '../css/BookListAllPage.css';

export default function BookListAllPage() {
  const navigate = useNavigate();
  const inputRef = useRef();

  const categories = ["전체","철학·종교","언어","과학","문학·역사"];
  const [selectedCategory, setSelectedCategory] = useState("전체");
  const [books, setBooks]     = useState([]);
  const [loading, setLoading] = useState(true);

  // 페이징
  const [page, setPage] = useState(1);
  const perPage = 10;
  const total   = books.length;
  const pages   = Math.ceil(total / perPage);

  useEffect(() => {
    setLoading(true);
    const fetcher = selectedCategory === "전체"
      ? getAllBooks()
      : getBooksByCategory(selectedCategory);

    fetcher
      .then(list => setBooks(list))
      .catch(() => setBooks([]))
      .finally(() => {
        setLoading(false);
        setPage(1);
      });
  }, [selectedCategory]);

  const handleSearch = e => {
    e.preventDefault();
    const kw = inputRef.current.value.trim();
    if (!kw) return alert('검색어를 입력하세요.');
    navigate(`/library/books?keyword=${encodeURIComponent(kw)}`);
  };

  if (loading) return <p className="loading-all">로딩 중…</p>;

  const start = (page - 1) * perPage;
  const slice = books.slice(start, start + perPage);

  return (
    <>
      <Header />
      <div className="BookListAllPage container-all">
        <Sidebar />
        <main className="main-all">
          {/* 검색창 */}
          <form className="search-wrap" onSubmit={handleSearch}>
            <input
              ref={inputRef}
              className="search-input"
              placeholder="검색어를 입력하세요."
            />
            <button type="submit" className="search-btn">
              <img
                src="/images/icons/search-icon.png"
                alt="검색"
                className="search-icon"
              />
            </button>
          </form>

          {/* 카테고리 */}
          <nav className="category-nav">
            {categories.map(cat => (
              <button
                key={cat}
                className={`category-tab${cat === selectedCategory ? ' active' : ''}`}
                onClick={() => setSelectedCategory(cat)}
              >
                {cat}
              </button>
            ))}
          </nav>

          {/* 요약 */}
          <div className="summary">
            {start + 1} – {Math.min(start + perPage, total)}건 출력 / 총 {total}건
          </div>

          {/* 그리드 */}
          <div className="grid-all">
            {slice.map(b => (
              <div key={b.bookId} className="card-all">
                <Link to={`/library/books/${b.bookId}`}>
                  <img
                    className="card-img"
                    src={b.bookImg.startsWith('/') ? b.bookImg : '/' + b.bookImg}
                    alt={b.title}
                  />
                </Link>
                <div className="card-info">
                  <h3 className="card-title">{b.title}</h3>
                  <p className="card-meta">저자: {b.author}</p>
                  <p className="card-meta">출판사: {b.publisher}</p>
                </div>
              </div>
            ))}
          </div>

          {/* 페이징 */}
          <div className="pagination-all">
            <button onClick={() => setPage(p => Math.max(p - 1, 1))}
                    disabled={page === 1}>
              이전
            </button>
            <span className="page-indicator">{page} / {pages} 페이지</span>
            <button onClick={() => setPage(p => Math.min(p + 1, pages))}
                    disabled={page === pages}>
              다음
            </button>
          </div>
        </main>
      </div>
      <Footer />
    </>
  );
}