import axios from "axios";
import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UserContext } from "../App";
import "../css/Header.css";

const Header = () => {
  const navigate = useNavigate();
  const { loginStatus, setLoginStatus } = useContext(UserContext);
  const { isLogin, loginUser } = loginStatus;

  const sessionUserInfo = JSON.parse(sessionStorage.getItem("sUserInfo"));
  const userName = loginUser?.name || sessionUserInfo?.name || "사용자";

  const handleLogout = async () => {
    try {
      await axios.get("/userinfo/logout");
      setLoginStatus({
        isLogin: false,
        loginUser: null,
      });
      alert("로그아웃 되었습니다.");
      navigate("/main");
    } catch (error) {
      console.error("로그아웃 실패:", error);
      alert("로그아웃 실패");
    }
  };

  return (
    <header className="header-container">
      <div className="header-left">
        <Link to="/" className="logo">Smart Campus</Link>
      </div>

      <div className="header-right">
        {isLogin ? (
          <>
            <span className="user-name">{userName}님</span>
            <Link to="/userpage" className="nav-button">내정보</Link>
            <button onClick={handleLogout} className="nav-button">로그아웃</button>
          </>
        ) : (
          <Link to="/login" className="nav-link">로그인</Link>
        )}
      </div>
    </header>
  );
};

export default Header;