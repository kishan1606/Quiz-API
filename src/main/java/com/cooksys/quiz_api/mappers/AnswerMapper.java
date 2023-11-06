package com.cooksys.quiz_api.mappers;

import java.util.List;

import com.cooksys.quiz_api.dtos.AnswerResponseDto;
import com.cooksys.quiz_api.entities.Answer;

import com.cooksys.quiz_api.entities.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

  AnswerResponseDto entityToDto(Answer entity);

  List<AnswerResponseDto> entitiesToDtos(List<Answer> entities);

}
