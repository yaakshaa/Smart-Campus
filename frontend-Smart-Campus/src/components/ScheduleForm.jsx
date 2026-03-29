import React, { useState, useEffect } from "react";
import { createSchedule } from "../api/scheduleApi";
import { fetchClassrooms } from "../api/classroomApi";
import { fetchLectures } from "../api/lectureApi";

export default function ScheduleForm({ onSuccess }) {
    const [day, setDay] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [classroomId, setClassroomId] = useState("");
    const [lectureId, setLectureId] = useState("");
    const [classrooms, setClassrooms] = useState([]);
    const [lectures, setLectures] = useState([]);

    useEffect(() => {
        fetchClassrooms().then(setClassrooms);
        fetchLectures().then(setLectures);
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await createSchedule({ day, startTime, endTime, classroomId, lectureId });
        onSuccess();
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Day" value={day} onChange={e => setDay(e.target.value)} required />
            <input placeholder="Start Time" type="time" value={startTime} onChange={e => setStartTime(e.target.value)} required />
            <input placeholder="End Time" type="time" value={endTime} onChange={e => setEndTime(e.target.value)} required />
            <select value={classroomId} onChange={e => setClassroomId(e.target.value)} required>
                <option value="">Select Classroom</option>
                {classrooms.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
            </select>
            <select value={lectureId} onChange={e => setLectureId(e.target.value)} required>
                <option value="">Select Lecture</option>
                {lectures.map(l => <option key={l.id} value={l.name}>{l.name}</option>)}
            </select>
            <button type="submit">Add Schedule</button>
        </form>
    );
}
