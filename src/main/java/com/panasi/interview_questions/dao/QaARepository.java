package com.panasi.interview_questions.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.entity.QuestionAndAnswer;

@Repository
public interface QaARepository extends JpaRepository<QuestionAndAnswer, Integer> {
}
