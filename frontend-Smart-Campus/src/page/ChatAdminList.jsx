import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Navigate, useNavigate } from 'react-router-dom';

function ChatAdminList() {
  const [inquiry, setInquiry]=useState([]);
  const navigate=useNavigate();

  useEffect(() => {
  axios.get('http://localhost:8080/Chat/ChatInquiry')
    .then(response => {
      setInquiry(response.data);
    })
    .catch(error => console.error(error));
}, []);
  
  return (
    <div>
      <table border='1'>
        <thead>
        <tr>
          <th id='inqueryId'>문의 ID</th>
          <th>문의 제목</th>
          <th>문의 생성 날짜</th>
          <th>대답 여부</th>
          <th>대답 내용</th>
          </tr>
        </thead>
        <tbody >
          {inquiry.map((row, index) => (
				<tr>
					<td>{row.inquiryId}</td>
					<td style={{ color: "blue", textDecoration: "underline",textAlign: "left" }} onClick={() => navigate(`/admin/ChatAdminList/${row.inquiryId}`)}>{row.inquiryTitle}</td>
					<td>{row.inquiryCreatedAt}</td>
					<td>{row.inquiryAnswered}</td>
					<td>{row.inquiryAnswerContent}</td>
				</tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default ChatAdminList
