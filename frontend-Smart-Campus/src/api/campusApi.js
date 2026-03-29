import axios from "axios";
import axiosRetry from 'axios-retry';

//환경 변수 관리
const BASE_URL = process.env.REACT_APP_API_BASE || 'http://localhost:8080';
const KAKAO_KEY = process.env.REACT_APP_KAKAO_KEY;
const KAKAO_REST_KEY = process.env.REACT_APP_KAKAO_REST_KEY;

//Axios 인스턴스 생성(타임아웃 및 쿠키 설정 포함)
const campusApi = axios.create({
    baseURL: BASE_URL,
    timeout: 5000, // 5초 이상 응답 없으면 에러
    withCredentials: true, // 쿠키 기반 인증 사용 시
    headers: {
        'Content-Type': 'application/json',
    },
});

// 요청 자동 재시도 설정 (네트워크 오류 또는 5xx 상태코드)
axiosRetry(campusApi, {
    retries: 3,
    retryDelay: axiosRetry.exponentialDelay,
    retryCondition: error => axiosRetry.isNetworkOrIdempotentRequestError(error) ||
        error.response.status >= 500,
});

// 요청 인터셉터: 로딩 스피너 표시, 공통 헤더 처리 등
campusApi.interceptors.request.use(
    config => {
        // TODO: global spinner on
        return config;
    },
    error => Promise.reject(error)
);

// 응답 인터셉터: 에러 공통 처리, 권한 실패 처리
campusApi.interceptors.response.use(
    response => {
        // TODO: global spinner off
        return response;
    },
    error => {
        // TODO: global spinner off
        if (error.response) {
            const { status } = error.response;
            if (status === 401) {
                //TODO: 인증 실패 시 리다이렉트 또는 로그아웃 처리
            }
        }
        return Promise.reject(error);
    }
);

// 유틸 함수 래퍼: campusApi 호출 + .data + 공통 에러 핸들링
export async function getCampus(path, config) {

    try {
        const res = await campusApi.get(path, config);
        return res.data;
    } catch (err) {
        console.error("[API ERROR]", err);
        return []; // 🔧 오류 발생 시 기본값으로 빈 배열 반환
        throw err;
    }
}


async function postCampus(path, body, config) {
    try {
        const res = await campusApi.post(path, body, config);
        return res.data;
    } catch (err) {
        console.error("[API ERROR]", err);
        throw err;
    }
}

async function put(path, body, config) {
    try {
        const res = await campusApi.put(path, body, config);
        return res.data;
    } catch (err) {
        handleError(err);
    }
}


async function del(path, config) {
    try {
        const res = await campusApi.delete(path, config);
        return res.data;
    } catch (err) {
        handleError(err);
    }
}
// 공통 에러 핸들러
function handleError(error) {
    console.error('[API ERROR]', error);
    //TODO: 사용자 알림(Toast) 또는 로깅 서비스
    throw error;
}

export {
    campusApi,
    KAKAO_KEY,
    KAKAO_REST_KEY,
    /* getCampus, */
    postCampus,
    put,
    del,
};
