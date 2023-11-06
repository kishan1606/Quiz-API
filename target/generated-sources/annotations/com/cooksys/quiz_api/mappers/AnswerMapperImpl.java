package com.cooksys.quiz_api.mappers;

import com.cooksys.quiz_api.dtos.AnswerResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-24T09:54:36-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.9 (IBM Corporation)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public AnswerResponseDto entityToDto(Answer entity) {
        if ( entity == null ) {
            return null;
        }

        AnswerResponseDto answerResponseDto = new AnswerResponseDto();

        answerResponseDto.setId( entity.getId() );
        answerResponseDto.setText( entity.getText() );
        answerResponseDto.setCorrect( entity.isCorrect() );

        return answerResponseDto;
    }

    @Override
    public List<AnswerResponseDto> entitiesToDtos(List<Answer> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AnswerResponseDto> list = new ArrayList<AnswerResponseDto>( entities.size() );
        for ( Answer answer : entities ) {
            list.add( entityToDto( answer ) );
        }

        return list;
    }
}
