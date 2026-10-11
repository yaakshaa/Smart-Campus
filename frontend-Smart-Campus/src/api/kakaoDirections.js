import axios from "axios";
import { KAKAO_REST_KEY } from './campusApi';

// 출발·도착 좌표를 직선으로 연결하는 경로 생성
export async function fetchDrivingRoute(origin, destination) {
    
    const pathCoords = [
        new window.kakao.maps.LatLng(origin.lat, origin.lng),
        new window.kakao.maps.LatLng(destination.lat, destination.lng)
    ];
    
    return {distance: 0, 
            duration: 0, 
            pathCoords,
            startPoint: origin,
            endPoint: destination
        };

}
