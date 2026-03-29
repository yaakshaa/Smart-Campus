// src/pages/GradeInfoPage.jsx

import React, { useEffect, useState } from 'react';
import {
  fetchAllGrades,
  fetchGradesByTerm,
  fetchTermSummary
} from '../api/gradeInfoApi';
import axios from 'axios';
import MainLayout from '../layout/MainLayout';

const GradeInfoPage = () => {
  const [grades, setGrades] = useState([]);
  const [summary, setSummary] = useState(null);
  const [terms, setTerms] = useState([]);
  const [selectedTermId, setSelectedTermId] = useState(''); // ''은 전체

  const studentId = 1; // 실제 로그인된 사용자로 교체 예정

  // 학기 목록 불러오기
  useEffect(() => {
    const fetchTerms = async () => {
      const res = await axios.get('http://localhost:8080/api/terms');
      setTerms(res.data);
    };
    fetchTerms();
  }, []);

  // 성적 및 요약 불러오기
  useEffect(() => {
    const loadGrades = async () => {
      try {
        let data;
        if (selectedTermId) {
          data = await fetchGradesByTerm(studentId, selectedTermId);
        } else {
          data = await fetchAllGrades(studentId);
        }
        setGrades(data);

        const summaryData = await fetchTermSummary(studentId, selectedTermId || null);
        setSummary(summaryData);
      } catch (error) {
        console.error('성적 조회 실패:', error);
      }
    };

    loadGrades();
  }, [selectedTermId]);

  return (
    <MainLayout>
      <div>
        <h1>성적 조회</h1>

        <select
          value={selectedTermId}
          onChange={(e) => setSelectedTermId(e.target.value)}
        >
          <option value="">전체 학기</option>
          {terms.map((term) => (
            <option key={term.termId} value={term.termId}>
              {term.academicYear} {term.semester}
            </option>
          ))}
        </select>

        <h2>성적 요약</h2>
        {summary && (
          <ul>
            <li>신청학점: {summary.attemptedCredits}</li>
            <li>이수학점: {summary.earnedCredits}</li>
            <li>총점평균: {summary.gradeScoreAvg}</li>
            <li>평점평균: {summary.gradePointAvg}</li>
          </ul>
        )}

        <h2>과목별 성적</h2>
        <table border="1">
          <thead>
            <tr>
              <th>과목명</th>
              <th>총점</th>
              <th>평점</th>
              <th>등급</th>
            </tr>
          </thead>
          <tbody>
            {grades.map((g) => (
              <tr key={g.gradeInfoId}>
                <td>{g.subjectName}</td>
                <td>{g.gradeScore}</td>
                <td>{g.gradePoint}</td>
                <td>{g.gradeLetter}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </MainLayout>
  );
};

export default GradeInfoPage;
