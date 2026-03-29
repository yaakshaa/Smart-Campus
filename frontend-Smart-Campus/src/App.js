import React, { useState, useEffect, createContext } from "react";
import {
  Route,
  Routes,
  useNavigate,
  useParams,
  useSearchParams,
  Navigate,
} from "react-router-dom";

import { Link } from 'react-router-dom';

// Main 관련
import MainLayout from "./layout/MainLayout";
import MainPage from "./page/MainPage";

// User 관련
import { UserLoginFormPage } from "./page/UserLoginFormPage";
import { UserSignupFormPage } from "./page/UserSignupFormPage";
import UserPage from "./page/UserPage";
import * as userApi from "./api/userApi";
import * as responseStatusCode from "./api/responseStatusCode";

// Chat 관련
import ChatQuestion from "./components/ChatQuestion";
import ChatInquiry from "./components/ChatInquiry";
import ChatInquiryAnswer from "./components/ChatInquiryAnswer";
import ChatBoatCharClick from "./components/ChatBoatCharClick";
import ChatMainMenu from "./components/ChatMainMenu";
import ChatDistractorsButtonClick from "./components/ChatDistractorsButtonClick";
import ChatPage from "./page/ChatPage";
import ChatAdminList from "./page/ChatAdminList";
import ChatQnAChange from "./components/ChatQnAChange";
import ChatQnAList from "./components/ChatQnAList";


// SmartCampus 페이지
import RestaurantPage from "./page/RestaurantPage";
import MenuPage from "./page/MenuPage";
import MenuDetailPage from "./page/MenuDetailPage";
import AddMenuForm from "./components/AddMenuForm";
import EditMenuForm from "./components/EditMenuForm";

// 수강신청 관련
import LectureList from "./components/LectureList";
import SubjectList from "./components/SubjectList";
import ScheduleList from "./components/ScheduleList";
import EnrollmentList from "./components/EnrollmentList";
import ClassroomList from "./components/ClassroomList";

// 캠퍼스 지도
import MapPage from "./page/MapPage";

// 수강신청 page
import LecturePage from "./page/LecturePage";
import SubjectPage from "./page/SubjectPage";
import SchedulePage from "./page/SchedulePage";
import EnrollmentPage from "./page/EnrollmentPage";
import ClassroomPage from "./page/ClassroomPage";
import MyEnrollmentsPage from "./page/MyEnrollmentsPage";

// 공지사항
import NoticeListPage from "./page/NoticeListPage";
import NoticeCreatePage from "./page/NoticeCreatePage";
import NoticeDetailPage from "./page/NoticeDetailPage";
import NoticeEditPage from "./page/NoticeEditPage";

// 게시판
import PostListPage from "./page/PostListPage";
import PostWritePage from "./page/PostWritePage";
import PostViewPage from "./page/PostViewPage";

// 도서관
import LibraryMainPage from "./page/LibraryMainPage";
import BookListPage from "./page/BookListPage";
import BookListAllPage from "./page/BookListAllPage";
import BookDetailPage from "./page/BookDetailPage";

// 성적조회
import GradeInfoPage from "./page/GradeInfoPage";

// 관리자
import AdminMainPage from "./page/AdminMainPage";
import StudentListPage from "./page/StudentListPage";


// API
import { fetchRestaurants } from "./api/restaurantApi";
import { fetchMenus, deleteMenu, updateMenu } from "./api/menuApi";
import { fetchReviews, createReview, deleteReview } from "./api/reviewApi";


export const UserContext = createContext({});

function App() {
  const navigate = useNavigate();

  const [loginStatus, setLoginStatus] = useState({
    isLogin: false,
    loginUser: null,
  });

  const isLogin = loginStatus.isLogin;
  const isAdmin = loginStatus.loginUser?.role === "ADMIN";
  const userId = loginStatus.loginUser?.userId;
  const [selectedDate, setSelectedDate] = useState(getToday());

  const [restaurants, setRestaurants] = useState([]);
  const [menus, setMenus] = useState([]);
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const checkLogin = async () => {
      try {
        const res = await userApi.userLoginCheck();
        if (res.status === responseStatusCode.LOGIN_CHECK_SUCCESS_USER) {
          sessionStorage.setItem("sUserInfo", JSON.stringify(res.data));
          setLoginStatus({
            isLogin: true,
            loginUser: res.data,
          });
        } else {
          sessionStorage.removeItem("sUserInfo");
          setLoginStatus({
            isLogin: false,
            loginUser: null,
          });
        }
      } catch (err) {
        console.error("세션 로그인 확인 실패:", err);
      }
    };
    checkLogin();
  }, []);

  useEffect(() => {
    fetchRestaurants().then(data => {
      setRestaurants(Array.isArray(data) ? data : []);
    });

    fetchMenus().then(data => {
      setMenus(Array.isArray(data) ? data : []);
    });

    fetchReviews().then(data => {
      setReviews(Array.isArray(data) ? data : []);
    });
  }, []);


  const handleEditMenu = (menuId) => {
    navigate(`/menu/${menuId}/edit`);
  };

  const handleDeleteMenu = async (menuId) => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;
    try {
      await deleteMenu(menuId);
      alert("삭제되었습니다.");
      const updatedMenus = await fetchMenus();
      setMenus(updatedMenus);
    } catch (err) {
      alert("삭제 실패: " + (err.response?.data?.message || err.message));
    }
  };

  const handleAddReview = async (review) => {
    try {
      await createReview({ ...review, userId });
      const updated = await fetchReviews();
      setReviews(updated);
    } catch (err) {
      alert("리뷰 등록 실패: " + (err.response?.data?.message || err.message));
    }
  };

  const handleDeleteReview = async (reviewId) => {
    if (!window.confirm("리뷰를 삭제하시겠습니까?")) return;
    try {
      await deleteReview(reviewId);
      const updated = await fetchReviews();
      setReviews(updated);
    } catch (err) {
      alert("리뷰 삭제 실패: " + (err.response?.data?.message || err.message));
    }
  };

  const FilteredMenuPage = () => {
    const { restaurantId } = useParams();
    const [searchParams] = useSearchParams();
    const selectedDate = searchParams.get("date");

    const filteredMenus = menus
      .filter(m => Number(m.restaurantId) === Number(restaurantId) && m.date === selectedDate)
      .map(menu => {
        const previewReview = reviews
          .filter(r => Number(r.menuId) === Number(menu.menuId))  // ✅ 중요
          .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))[0];

        return {
          ...menu,
          reviewPreview: previewReview?.reviewcomment || null
        };
      });

    return <MenuPage menus={filteredMenus} isAdmin={isAdmin} onEditMenu={handleEditMenu} onDeleteMenu={handleDeleteMenu} />;
  };


  const FilteredEditPage = () => {
    const { menuId } = useParams();
    const navigate = useNavigate();

    const menu = menus.find((m) => m.menuId === parseInt(menuId));

    const handleSubmit = async (updatedData) => {
      try {
        await updateMenu(menuId, { ...updatedData, restaurantId: menu.restaurantId });
        const updated = menus.map(m => m.menuId === parseInt(menuId) ? { ...m, ...updatedData } : m);
        setMenus(updated);
        alert("수정 완료!");
        navigate(`/menu/${menu.restaurantId}?date=${updatedData.date}`);
      } catch (err) {
        alert("수정 실패: " + (err.response?.data?.message || err.message));
      }
    };

    if (!menu) return <p>존재하지 않는 메뉴입니다.</p>;
    return <EditMenuForm menu={menu} onSubmit={handleSubmit} />;
  };

  const FilteredReviewPage = () => {
    const { menuId } = useParams();
    const menu = menus.find(m => m.menuId === parseInt(menuId));

    const relatedReviews = reviews
      .filter(r => r.menuId === parseInt(menuId))
      .map(r => ({
        reviewId: r.reviewId,          // ✅ DTO 기준
        userId: r.userId,              // ✅ DTO 기준
        rating: r.rating,
        reviewcomment: r.reviewcomment,
        createdAt: r.createdAt,
      }));

    const hasUserReviewed = relatedReviews.some(r => r.userId === userId);

    return (
      <MenuDetailPage
        menu={menu}
        reviews={relatedReviews}
        isAdmin={isAdmin}
        currentUserId={userId}
        onDeleteReview={handleDeleteReview}
        onAddReview={handleAddReview}
        hasUserReviewed={hasUserReviewed}
      />
    );
  };


  const MenuAddPage = () => {
    const { restaurantId } = useParams();
    const [searchParams] = useSearchParams();
    const selectedDate = searchParams.get("date");

    const handleSubmit = () => {
      navigate(`/menu/${restaurantId}?date=${selectedDate}`);
    };

    return <AddMenuForm restaurantId={restaurantId} onSubmit={handleSubmit} />;
  };

  return (
    <UserContext.Provider value={{ loginStatus, setLoginStatus, isLogin }}>
      <div className="App">
        <ChatBoatCharClick />

        <Routes>
          <Route path="/" element={<Navigate to="/main" replace />} />
          <Route path="/main" element={<MainPage />} />
          <Route path="/login" element={<UserLoginFormPage />} />
          <Route path="/signup" element={<UserSignupFormPage />} />
          <Route path="/userPage" element={<UserPage />} />

          <Route path="/Chat" element={<ChatPage />} />
          <Route path="/ChatMainMenu" element={<ChatMainMenu />} />
          <Route path="/ChatQuestion" element={<ChatQuestion />} />
          <Route path="/ChatMainMenu/:id/ChatDistractors" element={<ChatDistractorsButtonClick />} />
          <Route path="/Chat/answer/:distractorId" element={<ChatDistractorsButtonClick />} />
          <Route path="/ChatInquiry" element={<ChatInquiry />} />
          <Route path="/ChatInquiryAnswer" element={<ChatInquiryAnswer />} />


          <Route path="/admin/ChatAdminList/" element={<ChatAdminList />} />
          <Route path="/admin/ChatAdminList/:inqueryId" element={<ChatInquiryAnswer />} />
          <Route path="/admin/ChatQnAChange" element={<ChatQnAChange />} />
          <Route path="/admin/ChatQnAList" element={<ChatQnAList />} />
          <Route path="/admin/ChatQnAChange/:distractorId" element={<ChatQnAChange />} />


          {/* ✅ SmartCampus 관련 페이지만 MainLayout 적용 */}

          <Route path="/restaurants" element={
            <MainLayout>
              <RestaurantPage
                restaurants={restaurants}
                isAdmin={isAdmin}
                selectedDate={selectedDate}
                setSelectedDate={setSelectedDate}
              />
            </MainLayout>
          } />
          <Route path="/menu/:restaurantId" element={<MainLayout><FilteredMenuPage /></MainLayout>} />
          <Route path="/menu/:restaurantId/add" element={<MainLayout><MenuAddPage /></MainLayout>} />
          <Route path="/menu/:menuId/reviews" element={<MainLayout><FilteredReviewPage /></MainLayout>} />
          <Route path="/menu/:menuId/edit" element={<MainLayout><FilteredEditPage /></MainLayout>} />

          {/* 수강신청, 공지사항, 도서관 등 */}
          <Route path="/lectures" element={<LecturePage />} />
          <Route path="/subjects" element={<SubjectPage />} />
          <Route path="/enrollments" element={<EnrollmentPage />} />
          <Route path="/classrooms" element={<ClassroomPage />} />
          <Route path="/schedules" element={<SchedulePage />} />

          <Route path="/my-enrollments" element={<MyEnrollmentsPage userId={loginStatus.loginUser?.userId} />} />

          <Route path="/grade-info" element={<GradeInfoPage />} />

          <Route path="/map" element={<MapPage />} />
          <Route path="/notices" element={<NoticeListPage />} />
          <Route path="/notices/create" element={<NoticeCreatePage />} />
          <Route path="/notices/:noticeNo" element={<NoticeDetailPage />} />
          <Route path="/notices/edit/:noticeNo" element={<NoticeEditPage />} />

          <Route path="/posts" element={<PostListPage />} />
          <Route path="/posts/write" element={<PostWritePage />} />
          <Route path="/posts/:postNo" element={<PostViewPage />} />
          <Route path="/posts/edit/:postNo" element={<PostWritePage />} />

          <Route path="/library" element={<LibraryMainPage />} />
          <Route path="/library/books" element={<BookListPage />} />
          <Route path="/library/books/bookLists" element={<BookListAllPage />} />
          <Route path="/library/books/:bookId" element={<BookDetailPage />} />

          <Route path="/admin" element={<AdminMainPage />} />
          <Route path="/admin/students" element={<StudentListPage />} />
        </Routes>
      </div>
    </UserContext.Provider>
  );
}

function getToday() {
  const today = new Date();
  return today.toISOString().split("T")[0];
}

export default App;
