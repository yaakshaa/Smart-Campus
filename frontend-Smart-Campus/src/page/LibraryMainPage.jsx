import React, {useState, useRef} from "react";
import { Link, useNavigate } from "react-router-dom";
import Header  from "../layout/Header";
import Sidebar from "../layout/Sidebar";
import Footer  from "../layout/Footer";
import IconButton from "../components/IconButton";
import smartCampusImage from "../image/smartCampus.jpg";
import "../css/LibraryMainPage.css";

function LibraryMainPage() {
const [isHoursOpen, setIsHoursOpen] = useState(false);
const scrollRef = useRef(null);
const inputRef = useRef(null);              
const navigate = useNavigate();

const toggleHours = () => setIsHoursOpen(o => !o);

  // 검색 처리 함수
  const handleSearch = (e) => {
    e.preventDefault();                        // 폼 기본 제출 방지
    const kw = inputRef.current.value.trim();  // ref 로 value 가져오기

    if (!kw) {
      alert("검색어를 입력하세요.")
      return;
    }
    navigate(`/library/books?keyword=${encodeURIComponent(kw)}`)
  };

  const recentBooks = [
    { id: 1, title: "혼자 공부하는 자바", img: "/images/covers/book_img1.jpg" },
    { id: 5, title: "광기의 역사", img: "/images/covers/book_img5.jpg"},
    { id: 10, title: "책의 역사", img: "/images/covers/book_img10.jpg"},
    { id: 3, title: "하나님, 그래서 그러셨군요!", img: "/images/covers/book_img3.jpg"},
    { id: 9, title: "명작 스마트 소설", img: "/images/covers/book_img9.jpg"},
    { id: 4, title: "종교 사회학", img: "/images/covers/book_img4.jpg"},
  ];

  return (
    <>
      <Header />
      <div style={{ display: "flex" }}>
        <Sidebar />

        <main className="LibraryMainPage" style={{ flex: 1 }}>
          {/* ── Hero + 검색창 ───────────────── */}
          <section className="lib-hero"
                style={{
                    background: `url(${smartCampusImage}) center/cover no-repeat`}}
                >
              {/* ★ form 으로 감싸기 */}
            <form className="lib-search-wrap" onSubmit={handleSearch}>
              <input
                ref={inputRef}                     // ★ ref 연결
                className="lib-search-input"
                placeholder="검색어를 입력하세요."
              />
              <button
                type="submit"                      // ★ submit 버튼
                className="lib-search-btn"
              >
                <img src="/images/icons/search-icon.png" alt="검색" style={{width: 45, height: 45}}
                />
              </button>
              </form>
          </section>

          {/* ── 기능 아이콘 ────────────────── */}
          <section className="lib-icon-grid">
            <IconButton
                title="도서목록"
                to="/library/books/bookLists"
                imgSrc="/images/icons/book-icon.png"
            />
            <IconButton
                title="스터디룸 예약"
                to="/library/reserve"
                imgSrc="/images/icons/studyroom-icon.png"
            />
            {/* ── 개관시간 (직접 처리) ───────────────── */}
            <div
              className="lib-icon-item lib-icon-with-popover"
              onClick={toggleHours}
            >
              <img
                src="/images/icons/time-icon.png"
                alt="개관 시간"
                className="lib-icon-svg"
              />
              <span className="lib-icon-title">개관 시간</span>

              {isHoursOpen && (
                <div className="hours-popover">
                  <ul className="hours-list">
                    <li><strong>중앙도서관</strong> 09:00 – 19:00</li>
                    <li><strong>스터디룸</strong> 09:00 – 23:00</li>
                  </ul>
                </div>
              )}
            </div>
            <IconButton
                title="마이페이지"
                to="/library/mypage"
                imgSrc="/images/icons/user-icon.png"
            />
            <IconButton
                title="관리자"
                to="/admin/books/new"
                imgSrc="/images/icons/admin-icon.png"
                />
          </section>

          {/* ── 신착 도서 가로 스크롤 영역 ───────────────── */}
          <section className="recent-scroll-wrap">
        <div className="recent-scroll-header">
          <h3 className="recent-scroll-title">신착 도서</h3>
          {/* 전체보기 */}
          <Link to="/library/books/bookLists" className="recent-see-all">
            전체보기 +
          </Link>
        </div>

        <div className="recent-scroll-container">
          <div
            className="recent-scroll"
            ref={scrollRef}
            style={{
              justifyContent:
                recentBooks.length < 4 ? "center" : "flex-start"
            }}
          >
            {recentBooks.map((b) => (
              <div
                key={b.id}
                className="book-card"
                onClick={() => navigate(`/library/books/${b.id}`)}
                style={{ cursor: "pointer" }}
              >
                <img src={b.img}
                     alt={b.title}
                     className="book-cover" />
                <p className="book-title">{b.title}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

        </main>
      </div>
      <Footer />
    </>
  );
}

export default LibraryMainPage;