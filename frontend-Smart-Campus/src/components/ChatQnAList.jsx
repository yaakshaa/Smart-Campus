import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
//리스트 보여주기

function ChatQnAList() {
    const [QnAList, setQnAList] = useState([]);
    const navigate = useNavigate();
 
    const addQnAButton = () => {
        navigate('/admin/ChatQnAChange/0');
    }

    useEffect(() => {
        axios.get('http://localhost:8080/Chat/ChatDistractor')
            .then(response => {
                console.log(response.data);
                setQnAList(response.data);
            })
            .catch(error => console.error('QnA 전체조회 실패:', error));
    }, []);

    return (
        <div>
            <button onClick={addQnAButton}>QnA추가하기</button>
            <table border='1'>
                <thead>
                    <tr>
                        <th id='questionId'>질문id</th>
                        <th>질문명</th>
                        <th>대답내용</th>
                        <th>대답순서</th>
                        <th>대답링크</th>
                    </tr>
                </thead>
                <tbody >
                    {QnAList.map((row, index) => (
                        <tr>
                            <td>{row.questionId}</td>
                            <td >{row.questionContent}</td>
                            <td style={{ color: "blue", textDecoration: "underline",textAlign: "left" }} onClick={() => navigate(`/admin/ChatQnAChange/${row.distractorId}`)}>{row.distractorContent}</td>
                            <td>{row.distractorDisplayOrder}</td>
                            <td style={{textAlign: "left" }}>{row.distractorLink}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}


export default ChatQnAList
