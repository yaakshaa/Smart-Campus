import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ChatInquiry from '../components/ChatInquiry';

function ChatMainMenu() {
  const [menus, setMenus] = useState([]);
  const navigate = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);

  const openModal = () => setModalOpen(true);
  const closeModal = () => setModalOpen(false);

  useEffect(() => {
    axios.get('http://localhost:8080/Chat/ChatMainMenu')
      .then(response => setMenus(response.data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div style={{ marginBottom: '20px', textAlign: 'center' }}>
      <h3>메인 메뉴</h3>
      <div
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(3, 1fr)', // 3열
          gap: '8px',                            // 간격 줄이기
          maxWidth: '400px',
          margin: '0 auto',
          justifyItems: 'center'
        }}
      >
        {menus.map(menu => (
          <button
            key={menu.menuId}
            onClick={() => navigate(menu.menuLink)}
            style={{
              padding: '10px 14px',
              fontSize: '15px',
              backgroundColor: '#043c6c',
              color: 'white',
              border: 'none',
              borderRadius: '6px',
              cursor: 'pointer',
              width: '100%' // 버튼 크기 통일
            }}
          >
            {menu.menuName}
          </button>
        ))}

        {/* 관리자에게 문의하기 버튼 */}
        <button
          key="contact"
          onClick={openModal}
          style={{
            padding: '10px 14px',
            fontSize: '15px',
            backgroundColor: '#043c6c',
            color: 'white',
            border: 'none',
            borderRadius: '6px',
            cursor: 'pointer',
            width: '100%'
          }}
        >
          관리자에게<br />문의하기
        </button>
      </div>

      {/* 문의 모달 */}
      <ChatInquiry open={modalOpen} close={closeModal} header="Modal heading" />
    </div>
  );
}

export default ChatMainMenu;
