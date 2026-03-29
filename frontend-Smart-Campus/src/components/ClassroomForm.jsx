import React, { useState } from "react";
import { createClassroom } from "../api/classroomApi";

export default function ClassroomForm({ onSuccess }) {
    const [name, setName] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        await createClassroom({ name });
        onSuccess();
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Classroom Name" value={name} onChange={e => setName(e.target.value)} required />
            <button type="submit">Add Classroom</button>
        </form>
    );
}
