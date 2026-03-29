import React from 'react';
import { useNavigate } from 'react-router-dom';

function AdminHeader() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/admin'); 
  };

  return (
    <header
      style={{ background: '#333', color: '#fff', padding: '10px' }}>
     
      <h1> <span
        style={{cursor: 'pointer'}} onClick={handleClick}>🌟 관리자 페이지
        </span>
        </h1>
    </header>
  );
}

export default AdminHeader;
