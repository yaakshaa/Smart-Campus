import axios from 'axios';

// 세션 기반 로그인 유지 시 필요
axios.defaults.withCredentials = true;

// 회원가입
export const userSignup = async (sendJsonObject) => {
  const response = await axios.post('/userinfo', sendJsonObject, {
    headers: {
      'Content-Type': 'application/json'
    }
  });
  return response.data;
};

// 로그인
export const userLoginAction = async (sendJsonObject) => {
  const params = new URLSearchParams();
  params.append('userId', sendJsonObject.userId);
  params.append('password', sendJsonObject.password);

  const response = await axios.post('/userinfo/login', params, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials: true // 세션 쿠키 전달
  });
  return response.data;
};

// 로그인 상태 확인
export const userLoginCheck = async () => {
  const response = await axios.get('/userinfo/loginCheck', {
    withCredentials: true // ✅ 세션 유지에 꼭 필요
  });
  return response.data;
};

// 비밀번호 중복 검사 
export const checkPasswordDuplicate = async (password) => {
  const response = await axios.get("/userinfo/check-password", {
    params: { password }
  });
  return response.data; // true (중복), false (사용 가능)
};