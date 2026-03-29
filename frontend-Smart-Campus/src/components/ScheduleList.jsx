import React from 'react';

const ScheduleList = ({ schedules }) => {
  return (
    <div>
      <h2 className="text-xl font-bold mb-2">시간표 목록</h2>
      <ul className="space-y-2">
        {schedules.map((schedule) => (
          <li key={schedule.id} className="border p-2 rounded">
            강의명: {schedule.lecture?.lectureName} / 요일: {schedule.day} / 시간: {schedule.time} / 강의실: {schedule.classroom?.classroomName}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ScheduleList;
