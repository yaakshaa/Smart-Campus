import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import * as userApi from "../api/userApi";
import * as responseStatusCode from "../api/responseStatusCode";
import * as responseMessage from "../api/responseMessage";
import "../css/Signup.css";

export const UserSignupFormPage = () => {
  const signupFormRef = useRef();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    userId: "",
    password: "",
    name: "",
    email: "",
    userType: "학생" // 초기값은 한글
  });

  const [message, setMessage] = useState({
    userId: "",
    password: "",
    name: "",
    email: ""
  });

  const handleChangeSignupForm = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      [name]: value
    });
    setMessage({
      ...message,
      [name]: ""
    });
  };

  const userSignupAction = async () => {
    const f = signupFormRef.current;

    if (f.userId.value === "") {
      setMessage((prev) => ({ ...prev, userId: "아이디를 입력하세요" }));
      f.userId.focus();
      return;
    }

    if (f.password.value === "") {
      setMessage((prev) => ({ ...prev, password: "비밀번호를 입력하세요" }));
      f.password.focus();
      return;
    }

    if (f.name.value === "") {
      setMessage((prev) => ({ ...prev, name: "이름을 입력하세요" }));
      f.name.focus();
      return;
    }

    if (f.email.value === "") {
      setMessage((prev) => ({ ...prev, email: "이메일을 입력하세요" }));
      f.email.focus();
      return;
    }

    // ✅ userType 매핑 처리
    const userTypeMap = {
      학생: "STUDENT",
      교수: "PROFESSOR",
      관리자: "ADMIN"
    };

    const sendData = {
      ...user,
      userType: userTypeMap[user.userType] || "STUDENT" // 기본 fallback
    };

    try {
      const response = await userApi.userSignup(sendData);

      console.log("UserSignupFormPage response:", response);

      switch (Number(response.status)) {
        case responseStatusCode.CREATED_USER:
          alert(response.message);
          setTimeout(() => {
            navigate("/login");
          }, 100);
          break;

        case responseStatusCode.CREATE_FAIL_EXISTED_USER:
          setMessage((prev) => ({
            ...prev,
            userId: response.message
          }));
          break;

        case responseStatusCode.CREATE_FAIL_EXISTED_PASSWORD:
          setMessage((prev) => ({
            ...prev,
            password: response.message
          }));
          break;

        case responseStatusCode.CREATE_FAIL_EXISTED_EMAIL:
          setMessage((prev) => ({
            ...prev,
            email: response.message
          }));
          break;

        default:
          alert("알 수 없는 오류: " + response.message);
          break;
      }
    } catch (error) {
      console.error("Signup error:", error);
      alert("회원가입 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-box">
        <h2>회원가입</h2>
        <form
          ref={signupFormRef}
          className="signup-form"
          onSubmit={(e) => e.preventDefault()}
        >
          <div className="input-group">
            <input
              type="text"
              name="userId"
              placeholder="아이디"
              value={user.userId}
              onChange={handleChangeSignupForm}
            />
            <span className="error">{message.userId}</span>
          </div>

          <div className="input-group">
            <input
              type="password"
              name="password"
              placeholder="비밀번호"
              value={user.password}
              onChange={handleChangeSignupForm}
            />
            <span className="error">{message.password}</span>
          </div>

          <div className="input-group">
            <input
              type="text"
              name="name"
              placeholder="이름"
              value={user.name}
              onChange={handleChangeSignupForm}
            />
            <span className="error">{message.name}</span>
          </div>

          <div className="input-group">
            <input
              type="email"
              name="email"
              placeholder="이메일"
              value={user.email}
              onChange={handleChangeSignupForm}
            />
            <span className="error">{message.email}</span>
          </div>

          <div className="input-group">
            <select
              name="userType"
              value={user.userType}
              onChange={handleChangeSignupForm}
            >
              <option value="학생">학생</option>
              <option value="교수">교수</option>
              <option value="관리자">관리자</option>
            </select>
            <span className="error">&nbsp;</span>
          </div>

          <div className="button-group">
            <button type="button" onClick={userSignupAction}>
              회원가입
            </button>
            <button
              type="button"
              className="secondary"
              onClick={() => navigate("/login")}
            >
              로그인 화면으로
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};