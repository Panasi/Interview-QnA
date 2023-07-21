package com.panasi.interview_questions.service.mappers;

import com.panasi.interview_questions.repository.dto.FullQuestionDto;
import com.panasi.interview_questions.repository.entity.Question;
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
public class FullQuestionMapperImpl implements FullQuestionMapper {

    @Autowired
    private FullAnswerMapper fullAnswerMapper;
    @Autowired
    private QuestionCommentMapper questionCommentMapper;

    @Override
    public FullQuestionDto toFullQuestionDto(Question question) {
        if ( question == null ) {
            return null;
        }

        FullQuestionDto fullQuestionDto = new FullQuestionDto();

        fullQuestionDto.setAnswers( fullAnswerMapper.answerListToFullAnswerDtoList( question.getAnswers() ) );
        fullQuestionDto.setAuthorId( question.getAuthorId() );
        fullQuestionDto.setAuthorName( question.getAuthorName() );
        fullQuestionDto.setCategoryId( question.getCategoryId() );
        fullQuestionDto.setComments( questionCommentMapper.toCommentDtos( question.getComments() ) );
        fullQuestionDto.setDate( question.getDate() );
        fullQuestionDto.setId( question.getId() );
        fullQuestionDto.setIsPrivate( question.getIsPrivate() );
        fullQuestionDto.setName( question.getName() );

        return fullQuestionDto;
    }

    @Override
    public List<FullQuestionDto> toFullQuestionDtos(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<FullQuestionDto> list = new ArrayList<FullQuestionDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( toFullQuestionDto( question ) );
        }

        return list;
    }
}
