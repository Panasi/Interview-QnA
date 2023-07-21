package com.panasi.interview_questions.service.mappers;

import com.panasi.interview_questions.repository.dto.AnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-20T15:55:14+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.300.v20221108-0856, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public AnswerDto toAnswerDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerDto.AnswerDtoBuilder answerDto = AnswerDto.builder();

        answerDto.authorId( answer.getAuthorId() );
        answerDto.authorName( answer.getAuthorName() );
        answerDto.date( answer.getDate() );
        answerDto.id( answer.getId() );
        answerDto.isPrivate( answer.getIsPrivate() );
        answerDto.name( answer.getName() );
        answerDto.questionId( answer.getQuestionId() );

        return answerDto.build();
    }

    @Override
    public List<AnswerDto> toAnswerDtos(List<Answer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<AnswerDto> list = new ArrayList<AnswerDto>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( toAnswerDto( answer ) );
        }

        return list;
    }

    @Override
    public Answer toAnswer(AnswerDto answerDto) {
        if ( answerDto == null ) {
            return null;
        }

        Answer.AnswerBuilder answer = Answer.builder();

        answer.authorId( answerDto.getAuthorId() );
        answer.authorName( answerDto.getAuthorName() );
        answer.date( answerDto.getDate() );
        answer.id( answerDto.getId() );
        answer.isPrivate( answerDto.getIsPrivate() );
        answer.name( answerDto.getName() );
        answer.questionId( answerDto.getQuestionId() );

        return answer.build();
    }
}
