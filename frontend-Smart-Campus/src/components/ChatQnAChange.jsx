import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function ChatQnAChange() {
    const { distractorId } = useParams();
    const navigate = useNavigate();

    const [QnAList, setQnAList] = useState([]);
    const [questionId, setQuestionId] = useState('');
    const [questionContent, setQuestionContent] = useState('');
    const [distractorContent, setDistractorContent] = useState('');
    const [distractorDisplayOrder, setDistractorDisplayOrder] = useState('');
    const [distractorLink, setDistractorLink] = useState('');




    useEffect(() => {
        if (distractorId !== '0') {
            axios.get(`http://localhost:8080/Chat/ChatDistractor/${distractorId}`)
                .then(response => {
                    console.log(response.data[0]);
                    const data = response.data[0];
                    setQuestionId(data.questionId);
                    setQuestionContent(data.questionContent);
                    setDistractorContent(data.distractorContent);
                    setDistractorDisplayOrder(data.distractorDisplayOrder);
                    setDistractorLink(data.distractorLink);
                });

        } else {

            axios.get(`http://localhost:8080/Chat/ChatQuestion`).then((response) => {
                console.log(response.data);
                setQnAList(response.data)
            })
            // 신규 등록일 때 빈 상태 유지
            setQuestionContent('');
            setDistractorContent('');
            setDistractorDisplayOrder('');
            setDistractorLink('');
        }
    }, [distractorId]);

    const setQuestionIdOnchage = () => {
    };

    const setQuestionContentOnchage = () => {
        axios.get(`http://localhost:8080/Chat/ChatDistractor`)
            .then((response) => {

            })

    };


    const handleSave = () => {
        if (distractorId !== '0') {
            axios.put(`http://localhost:8080/Chat/ChatDistractor`,
                {
                    distractorId: distractorId,
                    questionId: questionId,
                    distractorContent: distractorContent,
                    distractorDisplayOrder: distractorDisplayOrder,
                    distractorLink: distractorLink
                }
            )
                .then(response => {
                    if (response.status == 200) { alert("저장되었습니다") }
                    else {
                        alert("저장에 실패했습니다")
                    }

                    console.log(response)
                });
        } else {
            axios.post(`http://localhost:8080/Chat/ChatDistractor/save`, {
                questionId: questionId ? questionId : 1,
                distractorContent: distractorContent,
                distractorDisplayOrder: distractorDisplayOrder,
                distractorLink: distractorLink
            })
                .then(response => {
                    if (response.status == 200) { alert("저장되었습니다") }
                    else {
                        alert("저장에 실패했습니다")
                    }
                });
        }
    };

    const handleDelete = () => {
        axios.delete(`http://localhost:8080/Chat/ChatDistractor/${distractorId}`)
            .then(response => {
                if (response.status === 200) {
                    alert("삭제되었습니다");
                    navigate('/admin/ChatQnAList'); // 삭제 후 리스트로 이동
                } else {
                    alert("삭제에 실패했습니다");
                }
            })
            .catch(error => {
                console.error(error);
                alert("삭제 중 오류가 발생했습니다");
            });
    };
    const questionHandleChange = (e) => {
        setQuestionId(e.target.value);
    }

    return (
        <div>
            <button onClick={() => navigate('/admin/ChatQnAList')}>QnAList로 돌아가기</button>

            <h3>답변지 추가하기</h3>
            <table border="1">
                <tbody>
                    <tr>
                        <td>질문id</td>
                        <td >{distractorId !== '0' ? questionId : <select onChange={questionHandleChange}>
                            {QnAList.map((row, index) => (
                                <option value={row.questionId}>{row.questionContent}</option>
                            ))}
                        </select>}
                        </td>
                        <td>답지id</td>
                        <td >{distractorId}</td>
                    </tr>
                    <tr>
                        <td>질문명</td>
                        <td colSpan={3}>{questionContent}</td>
                    </tr>
                    <tr>
                        <td>대답내용</td>
                        <td colSpan={3}>
                            <input style={{ width: "100%" }}
                                value={distractorContent}
                                onChange={e => setDistractorContent(e.target.value)}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>대답순서</td>
                        <td colSpan={3}>
                            <input style={{ width: "100%" }}
                                value={distractorDisplayOrder}
                                onChange={e => setDistractorDisplayOrder(e.target.value)}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>대답링크</td>
                        <td colSpan={3}>
                            <input style={{ width: "100%" }}
                                value={distractorLink}
                                onChange={e => setDistractorLink(e.target.value)}
                            />
                        </td>
                    </tr>
                </tbody>
            </table>
            <div>
                <button onClick={handleSave}>저장</button>
                {distractorId !== '0' && <button onClick={handleDelete}>삭제</button>}
            </div>

        </div>
    );
}

export default ChatQnAChange;
