import React, { useEffect, useState } from "react";
import axios from "axios";
import MainLayout from "../layout/MainLayout";
import "../css/Userpage.css";

function UserPage() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        axios.get("/userinfo/userpage", { withCredentials: true })
            .then(res => {
                setUser(res.data.data);
            })
            .catch(err => {
                console.error("마이페이지 정보 조회 실패", err);
                alert("로그인이 필요합니다.");
            });
    }, []);

    return (
        <MainLayout>
            <div className="userpage-wrapper">
                <div className="userpage-container">
                    <h2 className="userpage-title">{user ? `${user.name} 님의 정보` : '내정보'}</h2>
                    {user ? (
                        <div className="userpage-info">
                            <div className="user-info-row"><strong>아이디:</strong> <span>{user.userId}</span></div>
                            <div className="user-info-row"><strong>이름:</strong> <span>{user.name}</span></div>
                            <div className="user-info-row"><strong>이메일:</strong> <span>{user.email}</span></div>
                            <div className="user-info-row"><strong>유형:</strong> <span>{user.role}</span></div>
                        </div>
                    ) : (
                        <p>불러오는 중입니다...</p>
                    )}
                </div>
            </div>
        </MainLayout>
    );
}

export default UserPage;