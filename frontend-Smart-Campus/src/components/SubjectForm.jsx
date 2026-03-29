import React, { useState } from "react";
import { createSubject } from "../api/subjectApi";

export default function SubjectForm({ onSuccess }) {
    const [name, setName] = useState("");
    const [type, setType] = useState("");
    const [credit, setCredit] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        await createSubject({
            subjectName: name,       // ✅ 백엔드 DTO에 맞춰 수정
            subjectType: type,       // ✅ 백엔드 DTO에 맞춰 수정
            credit: parseInt(credit, 10) // ✅ 숫자로 변환 (기본값이 문자열)
        });
        onSuccess();
    };
    

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Subject Name" value={name} onChange={e => setName(e.target.value)} required />
            <input placeholder="Type" value={type} onChange={e => setType(e.target.value)} required />
            <input type="number" placeholder="Credit" value={credit} onChange={e => setCredit(e.target.value)} required />
            <button type="submit">Add Subject</button>
        </form>
    );
}
