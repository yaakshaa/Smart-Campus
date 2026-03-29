import React from 'react';
import { Link } from 'react-router-dom';

function AdminSidebar() {
  return (
    <aside style={{ width: '200px', background: '#f0f0f0', padding: '10px' }}>
      <h3>메뉴</h3>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        <li><Link to="/main">학교 메인</Link></li>
        <li><Link to="/admin/students">학생 관리</Link></li>
        <li><Link to="/admin/banners">배너 관리</Link></li>
        <li><Link to="/admin/ChatQnAList">답변 관리</Link></li>
        <li><Link to="/admin/ChatAdminList">문의 관리</Link></li>
      </ul>
    </aside>
  );
}

export default AdminSidebar;
