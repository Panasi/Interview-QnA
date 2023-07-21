package com.panasi.interview_questions.service.mappers;

import com.panasi.interview_questions.repository.dto.QuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
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
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public QuestionDto toQuestionDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDto.QuestionDtoBuilder questionDto = QuestionDto.builder();

        questionDto.authorId( question.getAuthorId() );
        questionDto.authorName( question.getAuthorName() );
        questionDto.categoryId( question.getCategoryId() );
        questionDto.date( question.getDate() );
        questionDto.id( question.getId() );
        questionDto.isPrivate( question.getIsPrivate() );
        questionDto.name( question.getName() );

        return questionDto.build();
    }

    @Override
    public List<QuestionDto> toQuestionDtos(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionDto> list = new ArrayList<QuestionDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( toQuestionDto( question ) );
        }

        return list;
    }

    @Override
    public Question toQuestion(QuestionDto questionDto) {
        if ( questionDto == null ) {
            return null;
        }

        Question.QuestionBuilder question = Question.builder();

        question.authorId( questionDto.getAuthorId() );
        question.authorName( questionDto.getAuthorName() );
        question.categoryId( questionDto.getCategoryId() );
        question.date( questionDto.getDate() );
        question.id( questionDto.getId() );
        question.isPrivate( questionDto.getIsPrivate() );
        question.name( questionDto.getName() );

        return question.build();
    }
}
