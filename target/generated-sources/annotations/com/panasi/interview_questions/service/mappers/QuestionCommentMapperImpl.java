package com.panasi.interview_questions.service.mappers;

import com.panasi.interview_questions.repository.dto.QuestionCommentDto;
import com.panasi.interview_questions.repository.entity.QuestionComment;
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
public class QuestionCommentMapperImpl implements QuestionCommentMapper {

    @Override
    public QuestionCommentDto toCommentDto(QuestionComment comment) {
        if ( comment == null ) {
            return null;
        }

        QuestionCommentDto.QuestionCommentDtoBuilder questionCommentDto = QuestionCommentDto.builder();

        questionCommentDto.authorId( comment.getAuthorId() );
        questionCommentDto.content( comment.getContent() );
        questionCommentDto.date( comment.getDate() );
        questionCommentDto.id( comment.getId() );
        questionCommentDto.questionId( comment.getQuestionId() );
        questionCommentDto.rate( comment.getRate() );

        return questionCommentDto.build();
    }

    @Override
    public List<QuestionCommentDto> toCommentDtos(List<QuestionComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<QuestionCommentDto> list = new ArrayList<QuestionCommentDto>( comments.size() );
        for ( QuestionComment questionComment : comments ) {
            list.add( toCommentDto( questionComment ) );
        }

        return list;
    }

    @Override
    public QuestionComment toComment(QuestionCommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        QuestionComment.QuestionCommentBuilder questionComment = QuestionComment.builder();

        questionComment.authorId( commentDto.getAuthorId() );
        questionComment.content( commentDto.getContent() );
        questionComment.date( commentDto.getDate() );
        questionComment.id( commentDto.getId() );
        questionComment.questionId( commentDto.getQuestionId() );
        questionComment.rate( commentDto.getRate() );

        return questionComment.build();
    }
}
