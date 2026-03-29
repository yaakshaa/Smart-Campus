import React from 'react';

const ClassroomList = ({ classrooms }) => {
  return (
    <div>
      <h2 className="text-xl font-bold mb-2">강의실 목록</h2>
      <ul className="space-y-2">
        {classrooms.map((classroom) => (
          <li key={classroom.id} className="border p-2 rounded">
            강의실명: {classroom.classroomName} / 위치: {classroom.location}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ClassroomList;
