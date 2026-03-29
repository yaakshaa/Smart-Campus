import { useContext, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import * as userApi from "../api/userApi";
import * as responseStatusCode from "../api/responseStatusCode";
import * as responseMessage from "../api/responseMessage";
import { UserContext } from "../App";
import "../css/Login.css";

export const UserLoginFormPage = () => {
  const { setLoginStatus } = useContext(UserContext);
  const loginFormRef = useRef();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    userId: "",
    password: ""
  });

  const [message, setMessage] = useState({
    id: "",
    password: ""
  });

  const handleChangeLoginForm = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value
    });
  };

  const userLoginAction = async () => {
    const f = loginFormRef.current;

    if (f.userId.value === "") {
      alert("아이디를 입력하세요");
      f.userId.focus();
      return;
    }

    if (f.password.value === "") {
      alert("비밀번호를 입력하세요");
      f.password.focus();
      return;
    }

    try {
      const responseJsonObject = await userApi.userLoginAction(user);
      console.log("UserLoginFormPage response:", responseJsonObject);

      switch (responseJsonObject.status) {

        case responseStatusCode.LOGIN_SUCCESS_USER:
          alert("로그인에 성공했습니다!");

          // ✅ 사용자 정보 세션 저장
          sessionStorage.setItem("sUserInfo", JSON.stringify(responseJsonObject.data));
          console.log("sUserInfo로 세션 저장:", JSON.parse(sessionStorage.getItem("sUserInfo")));

          // ✅ studentDto가 있을 경우 세션에 추가 저장
          if (responseJsonObject.extra?.studentDto) {
            sessionStorage.setItem("sStudentInfo", JSON.stringify(responseJsonObject.extra.studentDto));
            console.log("sStudentInfo 저장 완료:", responseJsonObject.extra.studentDto);
          }

          setLoginStatus({
            isLogin: true,
            loginUser: responseJsonObject.data
          });

          navigate("/main");
          break;

        case responseStatusCode.LOGIN_FAIL_NOT_FOUND_USER:
          setMessage({
            id: responseMessage.LOGIN_FAIL_NOT_FOUND_USER
          });
          break;

        case responseStatusCode.LOGIN_FAIL_PASSWORD:
          setMessage({
            password: responseMessage.LOGIN_FAIL_PASSWORD
          });
          break;

        default:
          alert("알 수 없는 오류가 발생했습니다.");
      }
    } catch (error) {
      alert("서버 오류 또는 네트워크 오류입니다.");
      console.error(error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>로그인</h2>
        <form ref={loginFormRef} name="f" method="post">
          <div>
            <label>아이디</label>
            <input
              type="text"
              name="userId"
              value={user.userId}
              onChange={handleChangeLoginForm}
            />
            <span className="error">{message.id}</span>
          </div>

          <div>
            <label>비밀번호</label>
            <input
              type="password"
              name="password"
              value={user.password}
              onChange={handleChangeLoginForm}
            />
            <span className="error">{message.password}</span>
          </div>

          <div className="button-group">
            <button type="button" onClick={userLoginAction}>
              로그인
            </button>
            <Link to="/signup">
              <button type="button" className="secondary">
                회원가입
              </button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};