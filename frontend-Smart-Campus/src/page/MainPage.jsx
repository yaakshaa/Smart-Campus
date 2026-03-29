import React from 'react';
import Header from '../layout/Header';
import Sidebar from '../layout/Sidebar';
import Footer from '../layout/Footer';
import '../css/MainLayout.css';
import smartCampusImage from '../image/smartCampus.jpg';

function MainPage() {
    return (
        <div className="main-layout">
            <Header />
            <div className="body-layout">
                <Sidebar />
                <main className="main-content">
                    <img src={smartCampusImage} alt="스마트 캠퍼스 메인 이미지" className="main-image" />
                </main>
            </div>
            <Footer />
        </div>
    );
}

export default MainPage;