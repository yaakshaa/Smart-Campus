import React from 'react';

const SubjectList = ({ subjects }) => {
  return (
    <div>
      <h2 className="text-xl font-bold mb-2">과목 목록</h2>
      <ul className="space-y-2">
        {subjects.map((subject) => (
          <li key={subject.id} className="border p-2 rounded">
            과목명: {subject.subjectName} / 구분: {subject.subjectType} / 학점: {subject.credit}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SubjectList;
