package com.panasi.interview_questions.service.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;

@Component
public class QuestionMapperImpl implements QuestionMapper {
	@Override
	public QuestionDto toQuestionDto(Question question) {
		if (question == null) {
			return null;
		}
		QuestionDto questionDto = new QuestionDto();
		questionDto.setId(question.getId());
		questionDto.setQuestion(question.getQuestion());
		questionDto.setAnswer(question.getAnswer());
		questionDto.setCategory(question.getCategory());
		return questionDto;
	}
	
	@Override
	public List<QuestionDto> toQuestionDtos(List<Question> questions) {
		if (questions.isEmpty()) {
			return null;
		}
		List<QuestionDto> questionsDto = new ArrayList<>();
		questions.forEach(question -> {
			QuestionDto questionDto = new QuestionDto();
			questionDto.setId(question.getId());
			questionDto.setQuestion(question.getQuestion());
			questionDto.setAnswer(question.getAnswer());
			questionDto.setCategory(question.getCategory());
			questionsDto.add(questionDto);
		});
		return questionsDto;
	}
	
	@Override
	public Question toQuestion(QuestionDto questionDto) {
		if (questionDto == null) {
			return null;
		}
		Question question = new Question();
		question.setId(questionDto.getId());
		question.setQuestion(questionDto.getQuestion());
		question.setAnswer(questionDto.getAnswer());
		question.setCategory(questionDto.getCategory());
		return question;
	}
}
