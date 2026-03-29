import React from 'react'
import { useLocation, useNavigate } from 'react-router-dom';

function ChatBoatCharClick() {
    const navigate = useNavigate();
    const location = useLocation();

    if (location.pathname === '/ChatPage') {
        return null; 
    }
    
    const handleClick = () => {
        navigate('/Chat')
    };

    return (
        <div
            style={{
                position: 'fixed',
                bottom: '20px',
                right: '20px',
                cursor: 'pointer',
                zIndex: 1000,
                backgroundColor: 'transparent' 
            }}
        >
            <img 
                alt="챗봇 사용"
                src="/images/ItwillBoat.png" 
                onClick={handleClick}
                width={100}
            />
        </div>
    );
}

export default ChatBoatCharClick;
