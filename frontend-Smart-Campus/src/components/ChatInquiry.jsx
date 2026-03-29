import React, { useState } from 'react';
import '../css/ChatInquiryModal.css';
import axios from 'axios';

function ChatInquiry(props) {
  const { open, close } = props;
  const [title, setTitle] = useState('');
  const [email, setEmail] = useState('');
  const [content, setContent] = useState('');

  const onclickSendButton = () => {
    if (!email.trim() || !content.trim()) {
      alert('내용을 입력하세요');
      return;
    }

    axios.post('http://localhost:8080/Chat/ChatInquiry', {
      inquiryTitle: title,
      inquiryEmail: email,
      inquiryMessage: content,
    }).then((res) => {
      setEmail('');
      setTitle('');
      setContent('');
      alert('전송되었습니다. 관리자 확인 후에 메일 드리겠습니다.');
      close();
    });
  };

  return (
    <div className={open ? 'openModal modal' : 'modal'}>
      <section>
        <header>
          <span>관리자에게 문의하기</span>
          <button className="close" onClick={close}>
            &times;
          </button>
        </header>
        <main>
          <div className="form-group">
            <label htmlFor="email">이메일</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={e => setEmail(e.target.value)}
              placeholder="your@email.com"
            />
          </div>

          <div className="form-group">
            <label htmlFor="title">제목</label>
            <input
              id="title"
              type="text"
              value={title}
              onChange={e => setTitle(e.target.value)}
              placeholder="문의 제목을 입력하세요"
            />
          </div>

          <div className="form-group">
            <label htmlFor="content">내용</label>
            <textarea
              id="content"
              rows="6"
              value={content}
              onChange={e => setContent(e.target.value)}
              placeholder="문의 내용을 입력하세요"
            />
          </div>
        </main>
        <footer>
          <button id="SendButton" className="submit-btn" onClick={onclickSendButton}>
            보내기
          </button>
        </footer>
      </section>
    </div>
  );
}

export default ChatInquiry;
