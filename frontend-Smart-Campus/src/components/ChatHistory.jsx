import React, { useState } from 'react';
import axios from 'axios';
import ChatMainMenu from '../components/ChatMainMenu';
import { useNavigate } from 'react-router-dom';

function Chat() {
  const [keyword, setKeyword] = useState('');
  const [questions, setQuestions] = useState([]);        // 질문 리스트
  const [distractors, setDistractors] = useState([]);    // 선택지 리스트
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // 키워드로 질문 검색
  const searchQuestions = async () => {
    if (!keyword.trim()) return;
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/Chat/ChatQuestion', { questionContent: keyword });
      setLoading(false);

      const data = res.data;

      if (data.length === 1 && data[0].listChatDistractorDTO) {
        setQuestions([]);
        setDistractors(data[0].listChatDistractorDTO);
      } else {
        setQuestions(data);
        setDistractors([]);
      }
    } catch (error) {
      setLoading(false);
      console.error('질문 검색 오류', error);
    }
  };

  // 질문 버튼 클릭 → 선택지 가져오기
  const loadDistractors = async (questionId) => {
    try {
      const res = await axios.get(`http://localhost:8080/Chat/ChatQuestion/ChatDistractors/${questionId}`);
      setDistractors(res.data);
      setQuestions([]);
    } catch (error) {
      console.error('선택지 로드 오류', error);
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: 'auto', padding: 20 }}>
      <h2>챗봇</h2>
      <ChatMainMenu />

      {/* 검색 input과 버튼을 flex로 감싸서 한 줄에 */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginTop: '20px' }}>
        <input
          type="text"
          placeholder="키워드를 입력하세요 (ex: 도서관)"
          value={keyword}
          onChange={e => setKeyword(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && searchQuestions()}
          style={{ flex: 1, padding: '8px' }}
        />
        <button
          onClick={searchQuestions}
          style={{ padding: '8px 12px' }}
        >
          검색
        </button>
      </div>

      {loading && <p>로딩중...</p>}

      {/* 질문 리스트 */}
      {questions.length > 0 && (
        <div style={{ marginTop: 20 }}>
          <h3>관련 질문</h3>
          {questions.map(q => (
            <button
              key={q.questionId}
              onClick={() => loadDistractors(q.questionId)}
              style={{ display: 'block', margin: '6px 0', padding: '8px', width: '100%', textAlign: 'left' }}
            >
              {q.questionContent}
            </button>
          ))}
        </div>
      )}

      {/* 선택지 리스트 */}
      {distractors.length > 0 && (
        <div style={{ marginTop: 20 }}>
          <h3>선택지</h3>
          {distractors.map(d => (
            <button
              key={d.distractorId}
              onClick={() => window.location.href = d.distractorLink}
              style={{ display: 'block', margin: '6px 0', padding: '8px', width: '100%', textAlign: 'left' }}
            >
              {d.distractorContent}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}

export default Chat;
