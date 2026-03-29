// src/layout/MainLayout.jsx
import React from 'react';
import Header from './Header';
import Sidebar from './Sidebar';
import Footer from './Footer';
import '../css/MainLayout.css';

function MainLayout({ children }) {
  return (
    <div className="main-layout">
      <Header />
      <div className="body-layout">
        <Sidebar />
        <main className="main-content">
          {children} {/* 여기에 각자 콘텐츠 삽입 */}
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default MainLayout;