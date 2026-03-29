import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

// 관리자 문의 상세 보기 컴포넌트
function ChatInquiryAnswer() {
  const { inqueryId } = useParams();
  const [inquiryAnswered, setInquiryAnswered] = useState('');
  const [inquiryAnswerContent, setInquiryAnswerContent] = useState('');
  const [adminId, setAdminId] = useState('');
  const [repliedDate, setRepliedDate] = useState('');
  const [isAnswered, setIsAnswered] = useState(false);

  const [inquiry, setInquiry] = useState({
    inquiryId: '',
    inquiryTitle: '',
    inquiryEmail: '',
    inquiryMessage: '',
    inquiryCreatedAt: '',
  });

  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/Chat/ChatAdminList/${inqueryId}`)
      .then(response => {
        console.log(response.data[0]);
        setInquiry(response.data[0]);
      })
      .catch(error => console.error('문의 상세 조회 실패:', error));
  }, [inqueryId]);

  const goListButton = () => {
    navigate('/admin/ChatAdminList');
  };

  const replyOnchange = (e) => {
    setInquiryAnswerContent(e.target.value)
  }

  const replyOnbuttonClick = (e) => {
    if (!inquiryAnswerContent.trim()) {
      alert("답변 내용을 입력하세요");
      return;
    }
    axios.post('http://localhost:8080/Chat/ChatAdminList/Answer', {
      inquiryId: inquiry.inquiryId,
      inquiryAnswerContent: inquiryAnswerContent
    }).then((response) => {
      alert('전송되었습니다')
      setIsAnswered(true);
    }).catch((error) => {
      console.log('전송실패')
    })
  }

  return (
    <div>
      <h2>문의 상세</h2>
      <button id='goListButton' onClick={goListButton}>리스트보기</button>
      <table>
        <tbody>
          <tr>
            <th>문의 번호:</th>
            <td>{inquiry.inquiryId}</td>
          </tr>
          <tr>
            <th>문의 제목:</th>
            <td>{inquiry.inquiryTitle}</td>
          </tr>
          <tr>
            <th>문의 이메일:</th>
            <td>{inquiry.inquiryEmail}</td>
          </tr>
          <tr>
            <th>문의 날짜:</th>
            <td>{inquiry.inquiryCreatedAt}</td>
          </tr>
          <tr>
            <th>문의 내용:</th>
            <td>{inquiry.inquiryMessage}</td>
          </tr>

          <tr>
            <th>답변 내용: </th>
            <td><textarea rows='6' style={{width:"100%"}} value={inquiry.inquiryAnswerContent} onChange={replyOnchange} disabled={isAnswered}
            ></textarea></td>
          </tr>
        </tbody>
        {inquiry.inquiryAnswered=='Y'?'':<button id='reply' onClick={replyOnbuttonClick} disabled={isAnswered}>답변하기</button>}
      </table>
    </div>
  );
}

export default ChatInquiryAnswer;
