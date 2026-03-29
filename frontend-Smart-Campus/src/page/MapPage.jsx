import React, { useEffect, useState } from "react";
import Header from "../layout/Header";
import Sidebar from "../layout/Sidebar";
import Footer from "../layout/Footer";
import MapView from "../components/MapView";
import PathControl from "../components/PathControl";
import LoadingSpinner from "../components/LoadingSpinner";
import { getCampus } from "../api/campusApi";

/**
 * MapPage에서 상태 관리 및 컨트롤 컴포넌트 연결
 */
const styles = {
    container: {
        display: "flex",
        minHeight: "calc(100vh - 120px)",
    },
    main: {
        flex: 1,
        display: "flex",
        flexDirection: "column",
        padding: 16,
        boxSizing: "border-box",

    },
    controls: {
        flex: "0 0 auto",
        marginBottom: 16,
    },
    mapArea: {
        flex: 1,
        position: "relative",
    },
    pathPanel: {
        position: "absolute",
        top: "2opx",
        left: "20px",
        width: "280px",
        height: "calc(100% - 40px)",
        backgroundColor: "#ffffff",
        zIndex: 20,
        boxShadow: "0 4px 16px rgba(0,0,0,0.18)",
        borderRadius: "12px",
        padding: "20px",
        boxSizing: "border-box",
        overflowY: "auto"
    },
    pathButton: {
        position: "absolute",
        top: "24px",
        right: "40px",
        zIndex: 30,
        width: "58px",
        height: "58px",
        backgroundColor: "#003366",
        color: "#fff",
        border: "none",
        borderRadius: "0px",
        cursor: "pointer",
        boxShadow: "0 4px 12px rgba(0,0,0,0.18)",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        gap: "8px",
        padding: "0"
    },
    pathButtonIcon: {
        fontSize: "24px",
        lineHeight: 1,
        fontWeight: "400",
        color: "#fff"
    },
    pathButtonText: {
        fontSize: "12px",
        lineHeight: 1,
        fontWeight: "700",
        color: "#fff",
        letterSpacing: "-0.3px"
    },
    closeButton: {
        marginBottom: "16px",
        background: "none",
        border: "none",
        fontSize: "15px",
        fontWeight: "600",
        cursor: "pointer",
        color: "#333"
    },
    PathControl: {
        position: "absolute",
        top: 16,
        left: "50%",
        transform: "translateX(-50%)",
        zIndex: 1000,
        background: "#4B3AFF",
        borderRadius: 4,
        boxShadow: "0 2px 6px rgba(0,0,0,0.3)",
        padding: "4px 12px",
    },
    error: {
        color: "red",
        marginBottom: 8,
    },

};
export default function MapPage() {

    console.log("mappage 랜더됨");
    
    // 길찾기 결과 좌표 상태
    const [pathResult, setPathResult] = useState(null);

    const [isPathPanelOpen, setIsPathPanelOpen] = useState(false);

    console.log("MapPage pathResult:", pathResult);
    return (
        <>
            {/* 상단 헤더 */}
            <Header />
            <div style={styles.container}>
                {/* 좌측 사이드바 */}
                <Sidebar />
                {/* 메인 콘텐츠 영역 */}
                <main style={styles.main}>
    
                    <div style={styles.mapArea}>
                        {!isPathPanelOpen && (
                            <button
                                style={styles.pathButton}
                                onClick={() => setIsPathPanelOpen(true)}
                            >
                                <div style={styles.pathButtonIcon}>↱</div>
                                <div style={styles.pathButtonText}>길찾기</div>
                            </button>
                        )}

                        {isPathPanelOpen && (
                            <div style={styles.pathPanel}>
                                <button
                                    style={styles.closeButton}
                                    onClick={() => setIsPathPanelOpen(false)}
                                >
                                    ← 닫기
                                </button>

                                <PathControl onPathComputed={setPathResult}/>
                            </div>
                        )}
                        
                        {/* {지도 뷰에 상태 전달} */}
                        <MapView                          
                            pathResult={pathResult}
                        />
                    </div >
                </main >
            </div >
            <Footer />
        </>
    );

}
