import React, { useEffect, useRef } from 'react';
import { KAKAO_KEY, getCampus as get } from '../api/campusApi';

/**
 * MapView 컴포넌트
 * Props:
 * - centerLat, centerLng: 초기 지도 중심 좌표
 * - selectedFloorPlan: { imageUrl, centerLat, centerLng } or null
 * - pathCoords: kakao.maps.LatLng 객체 배열 or null
 */
export default function MapView({
    centerLat = 37.541234,
    centerLng = 126.986123,
    pathResult = null,
    
}) {
    const mapRef = useRef(null);
    const markersRef = useRef([]);
    const polylineRef = useRef(null);
    const infoWindowRef = useRef();
    const startMarkerRef = useRef(null);
    const endMarkerRef = useRef(null);

  const createLabeledMarkerImage = (color, label) => {
    const svg = `
        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="52" viewBox="0 0 40 52">
            <path d="M20 0C11 0 0 11 0 20c0 15 20 32 20 32s20-17 20-32C40 11 29 0 20 0z" fill="${color}"/>
            <text x="20" y="20" text-anchor="middle" font-size="10" font-weight="700" fill="white" font-family="Arial, sans-serif">${label}</text>
        </svg>
    `;
    const url = "data:image/svg+xml;charset=UTF-8," + encodeURIComponent(svg);

    return new window.kakao.maps.MarkerImage(
        url,
        new window.kakao.maps.Size(40, 52),
        { offset: new window.kakao.maps.Point(20, 52) }
    );
};
    // 맵 로딩, 마커&오버레이 처리
    useEffect(() => {
        let map;

        /* const script = document.createElement('script'); */
        /* script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${KAKAO_KEY}&autoload=false`; */
        console.log("Loaded Kakao Key:", KAKAO_KEY);
        const script = document.createElement('script');
        script.async = true;
        script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${KAKAO_KEY}&autoload=false`;
        document.head.appendChild(script);

        script.onload = () => {
            window.kakao.maps.load(() => {
                // 지도 생성
                map = new window.kakao.maps.Map(mapRef.current, {
                    center: new window.kakao.maps.LatLng(37.550909, 126.925547),
                    level: 4
                });
                window.__kakaoMapInstance = map;

                infoWindowRef.current = new window.kakao.maps.InfoWindow({
                    removable: true
                });

                // 지도 빈 곳 클릭하면 닫김
                window.kakao.maps.event.addListener(map, 'click', () => {
                    infoWindowRef.current.close();
                });
                // 확대/축소 컨트롤
                const zoomControl = new window.kakao.maps.ZoomControl();
                map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);

                // 건물 데이터 받아 마커 및 InfoWindow 생성
                get('/api/buildings')
                    .then(buildings => {
                        console.log('buildings 응답 개수:', buildings.length);
                        console.log('buildings 응답 전체:', buildings);

                        buildings.forEach(building => {
                            const marker = new window.kakao.maps.Marker({
                                position: new window.kakao.maps.LatLng(
                                    building.latitude, building.longitude
                                ),
                                map
                            });

                            markersRef.current.push(marker);

                            window.kakao.maps.event.addListener(marker, 'click', () => {
                                if (infoWindowRef.current) {
                                    infoWindowRef.current.close();
                                    infoWindowRef.current.setContent(`<div style="padding: 8px; min-width: 150px;">
                                        <strong>${building.buildingName}</strong><br/>
                                        ${building.description || ''}
                                        </div>`);
                                    infoWindowRef.current.open(map, marker);
                                }
                            });
                        });
                    })
                    .catch(err => console.error('API error:', err));

               
                // 전역 객체로 저장
                window.__kakaoMapInstance = map;
            });
        };

        // 언마운트 시 정리
        return () => {
            document.head.removeChild(script);
            // 마커 제거
            markersRef.current.forEach(m => m.setMap(null));
            // 폴리라인 제거
            if (polylineRef.current) polylineRef.current.setMap(null);
            // 출발/도착 마커 제거
            if (startMarkerRef.current) startMarkerRef.current.setMap(null);
            if(endMarkerRef.current) endMarkerRef.current.setMap(null);
           
        };
    }, []);
    console.log("MapView pathResult:", pathResult);
    // pathCoords 변경 시 폴리라인 그리기
    useEffect(() => {
        const map = window.__kakaoMapInstance;
        if(!map) return;

        if(polylineRef.current) {
            polylineRef.current.setMap(null);
        }

        if(startMarkerRef.current) {
            startMarkerRef.current.setMap(null);
        }

        if(endMarkerRef.current) {
            endMarkerRef.current.setMap(null);
        }

        if(pathResult && pathResult.pathCoords && pathResult.pathCoords.length) {
            polylineRef.current = new window.kakao.maps.Polyline({
                path: pathResult.pathCoords,
                strokeWeight: 5,
                strokeColor: "#FF5A5A",
                strokeOpacity: 0.8
            });
            polylineRef.current.setMap(map);

           const startMarkerImage = createLabeledMarkerImage("#2563EB", "출발");
           const endMarkerImage = createLabeledMarkerImage("#EF4444", "도착");

           startMarkerRef.current = new window.kakao.maps.Marker({
            position: new window.kakao.maps.LatLng(
                pathResult.startPoint.lat,
                pathResult.startPoint.lng
            ),
            map,
            title: "출발",
            image: startMarkerImage
           });

           endMarkerRef.current = new window.kakao.maps.Marker({
            position: new window.kakao.maps.LatLng(
                pathResult.endPoint.lat,
                pathResult.endPoint.lng
            ),
            map,
            title: "도착",
            image: endMarkerImage
           });

            
        }
    }, [pathResult]);

    return (
        <div
            ref={mapRef}
            style={{
                position: 'absolute',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%'
            }}
        />
    );
}
