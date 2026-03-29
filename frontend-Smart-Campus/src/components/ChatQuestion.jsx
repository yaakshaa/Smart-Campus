import React, { useState } from 'react';
import axios from 'axios';


function ChatQuestion({ onQuestionSaved }) {
  const [question, setQuestion] = useState('');

  const handleSubmit = async () => {
    if (!question.trim()) return;
    try {
      const res = await axios.post('http://localhost:8080/Chat/ChatQuestion', {  questionContent: question });

      console.log(res.data);

      onQuestionSaved(res.data);
      setQuestion('');
      
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="키워드를 입력하세요(ex:성적)"
        value={question}
        onChange={e => setQuestion(e.target.value)}
        onKeyDown={e => { if (e.key === 'Enter') handleSubmit(); }}
      />
      <button onClick={handleSubmit}>▶</button>
    </div>
  );
}

export default ChatQuestion;