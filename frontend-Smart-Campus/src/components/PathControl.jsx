import React, { useState, useEffect } from "react";

import LoadingSpinner from "./LoadingSpinner";
import { getCampus } from "../api/campusApi";
import { fetchDrivingRoute } from "../api/kakaoDirections";

const styles = {
  wrapper: {
    display: "flex",
    flexDirection: "column",
    gap: "10px"
  },
  title: {
    fontSize: "22px",
    fontWeight: "700",
    marginBottom: "10px"
  },
  label: {
    fontSize: "14px",
    fontWeight: "600",
    color: "#333"
  },
  select: {
    width: "100%",
    height: "44px",
    padding: "0 12px",
    border: "1px solid #d9d9d9",
    borderRadius: "6px",
    fontSize: "14px",
    outline: "none",
    backgroundColor: "#fff"
  },
  findButton: {
    width: "100%",
    height: "46px",
    marginTop: "10px",
    backgroundColor: "#243BFF",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    fontSize: "15px",
    fontWeight: "600",
    cursor: "pointer"
  },
  errorText: {
    color: "red",
    marginTop: "8px",
    fontSize: "14px"
  },
  
};

/**
 * PathControl 컴포넌트
 * - 건물 목록을 불러오고 출발/도착 선택 후 경로 요청
 * - 결과 좌표 배열을 onPathComputed에 전달
 */
export default function PathControl({ onPathComputed }) {
  const [buildings, setBuildings] = useState([]);
  const [loadingBuildings, setLoadingBuildings] = useState(true);
  const [errorBuildings, setErrorBuildings] = useState(null);

  const [startId, setStartId] = useState("");
  const [endId, setEndId] = useState("");
  

  const [loadingRoute, setLoadingRoute] = useState(false);
  const [errorRoute, setErrorRoute] = useState(null);

  // 건물 정보 초기 로드
  useEffect(() => {
    const fetchBuildings = async () => {
      setLoadingBuildings(true);
      setErrorBuildings(null);
      try {
        const data = await getCampus("/api/buildings");
        console.log("buildings data:", data);
        setBuildings(data);
      } catch (err) {
        setErrorBuildings("건물 목록을 불러오는데 실패했습니다.");
      } finally {
        setLoadingBuildings(false);
      }
    };
    fetchBuildings();
  }, []);

  const handleFind = async () => {
    if (!startId || !endId) return;

    const start = buildings.find(b => String(b.buildingId) === startId);
    const end = buildings.find(b => String(b.buildingId) === endId);
    if (!start || !end) return;

    setLoadingRoute(true);
    setErrorRoute(null);

    try {
      const result = await fetchDrivingRoute(
        { lat: start.latitude, lng: start.longitude },
        { lat: end.latitude, lng: end.longitude }
      );
      console.log("PathControl result:", result);
      onPathComputed(result);
    } catch (err) {
      setErrorRoute("길찾기 요청에 실패했습니다.");
    } finally {
      setLoadingRoute(false);
    }
  };

  if (loadingBuildings) return <LoadingSpinner />;
  if (errorBuildings) return <div style={styles.errorText}>{errorBuildings}</div>;

  return (
    <div style={styles.wrapper}>
      <h3 style={styles.title}>길찾기</h3>

      <label style={styles.label}>출발</label>
      <select 
        value={startId}
        onChange={e => setStartId(e.target.value)}
        style={styles.select}
        >
          <option value="">출발 건물 선택</option>
          {buildings.map(b => (
            <option key={b.buildingId} value={String(b.buildingId)}>
              {b.buildingName}
            </option>
          ))}
        </select>

        <label style={styles.label}>도착</label>
        <select
          value={endId}
          onChange={e => setEndId(e.target.value)}
          style={styles.select}
          >
            <option value="">도착 건물 선택</option>
            {buildings.map(b => (
              <option key={b.buildingId} value={String(b.buildingId)}>
                {b.buildingName}
              </option>
            ))}
          </select>

          <button 
            style={styles.findButton}
            onClick={handleFind}
            disabled={!startId || !endId || loadingRoute}
            >
              {loadingRoute ? "로딩중..." : "길찾기"}
            </button>

            {errorRoute && (
              <div style={styles.errorText}>
                {errorRoute}
              </div>
            )}
    </div>
  );
}