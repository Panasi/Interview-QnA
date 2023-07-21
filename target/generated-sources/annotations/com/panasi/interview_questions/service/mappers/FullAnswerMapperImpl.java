package com.panasi.interview_questions.service.mappers;

import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.entity.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-20T15:55:14+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.300.v20221108-0856, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class FullAnswerMapperImpl implements FullAnswerMapper {

    @Autowired
    private AnswerCommentMapper answerCommentMapper;

    @Override
    public FullAnswerDto toFullAnswerDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        FullAnswerDto fullAnswerDto = new FullAnswerDto();

        fullAnswerDto.setAuthorId( answer.getAuthorId() );
        fullAnswerDto.setAuthorName( answer.getAuthorName() );
        fullAnswerDto.setComments( answerCommentMapper.toCommentDtos( answer.getComments() ) );
        fullAnswerDto.setDate( answer.getDate() );
        fullAnswerDto.setId( answer.getId() );
        fullAnswerDto.setIsPrivate( answer.getIsPrivate() );
        fullAnswerDto.setName( answer.getName() );
        fullAnswerDto.setQuestionId( answer.getQuestionId() );

        return fullAnswerDto;
    }

    @Override
    public List<FullAnswerDto> answerListToFullAnswerDtoList(List<Answer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<FullAnswerDto> list = new ArrayList<FullAnswerDto>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( toFullAnswerDto( answer ) );
        }

        return list;
    }
}
