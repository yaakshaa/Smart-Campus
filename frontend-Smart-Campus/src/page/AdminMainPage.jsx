import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import AdminHeader from '../components/AdminHeader';
import AdminSidebar from '../components/AdminSidebar';


function AdminMainPage() {
  const navigate = useNavigate();

  return (
    <div>
      <AdminHeader />
      <div style={{ display: 'flex' }}>
        <AdminSidebar />
        <main style={{ padding: '20px', flexGrow: 1 }}>
          <h2>관리자 대시보드</h2>


          <section style={{ marginTop: '20px' }}>
            <h3>관리자만 접근가능</h3>
            <p>학생 인원수 추후 추가</p>
          </section>
        </main>
      </div>
    </div>
  );
}
export default AdminMainPage;
