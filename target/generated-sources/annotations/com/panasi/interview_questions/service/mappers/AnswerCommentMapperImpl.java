package com.panasi.interview_questions.service.mappers;

import com.panasi.interview_questions.repository.dto.AnswerCommentDto;
import com.panasi.interview_questions.repository.entity.AnswerComment;
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
public class AnswerCommentMapperImpl implements AnswerCommentMapper {

    @Override
    public AnswerCommentDto toCommentDto(AnswerComment comment) {
        if ( comment == null ) {
            return null;
        }

        AnswerCommentDto.AnswerCommentDtoBuilder answerCommentDto = AnswerCommentDto.builder();

        answerCommentDto.answerId( comment.getAnswerId() );
        answerCommentDto.authorId( comment.getAuthorId() );
        answerCommentDto.content( comment.getContent() );
        answerCommentDto.date( comment.getDate() );
        answerCommentDto.id( comment.getId() );
        answerCommentDto.rate( comment.getRate() );

        return answerCommentDto.build();
    }

    @Override
    public List<AnswerCommentDto> toCommentDtos(List<AnswerComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<AnswerCommentDto> list = new ArrayList<AnswerCommentDto>( comments.size() );
        for ( AnswerComment answerComment : comments ) {
            list.add( toCommentDto( answerComment ) );
        }

        return list;
    }

    @Override
    public AnswerComment toComment(AnswerCommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        AnswerComment.AnswerCommentBuilder answerComment = AnswerComment.builder();

        answerComment.answerId( commentDto.getAnswerId() );
        answerComment.authorId( commentDto.getAuthorId() );
        answerComment.content( commentDto.getContent() );
        answerComment.date( commentDto.getDate() );
        answerComment.id( commentDto.getId() );
        answerComment.rate( commentDto.getRate() );

        return answerComment.build();
    }
}
