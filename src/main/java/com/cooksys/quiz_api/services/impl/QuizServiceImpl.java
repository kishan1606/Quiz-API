package com.cooksys.quiz_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.BadRequestException;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    private Quiz getQuiz(Long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findByIdAndDeletedFalse(id);
        if (optionalQuiz.isEmpty()) {
            throw new NotFoundException("Quiz not Found");
        }
        return optionalQuiz.get();
    }

    private Question getQuestion(Long questionID) {
        Optional<Question> optionalQuestion = questionRepository.findByIdAndDeletedFalse(questionID);
        if (optionalQuestion.isEmpty()) {
            throw new NotFoundException("Question not Found");
        }
        return optionalQuestion.get();
    }

    @Override
    public List<QuizResponseDto> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findALlByDeletedFalse();
        for(Quiz quiz: quizzes){
            List<Question> notDeletedQuestions = new ArrayList<>();
            for(Question question: quiz.getQuestions()){
                if(!question.isDeleted()){
                    notDeletedQuestions.add(question);
                }
            }
            quiz.setQuestions(notDeletedQuestions);
        }
        return quizMapper.entitiesToDtos(quizzes);
    }

    @Override
    public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
        if(quizRequestDto == null || quizRequestDto.getName().trim().isEmpty() || quizRequestDto.getQuestions().isEmpty()){
            throw new BadRequestException("Please enter all required fields");
        }

        Quiz quiz = quizMapper.requestDtoToEntity(quizRequestDto);
        for (Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);
            question.setDeleted(false);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                answer.setCorrect(answer.isCorrect());
            }
        }
        quiz.setDeleted(false);
        Quiz returnQuiz = quizRepository.saveAndFlush(quiz);
        return quizMapper.entityToDto(returnQuiz);
    }

    @Override
    public QuizResponseDto deleteQuiz(Long id) {
        Quiz quizToDelete = getQuiz(id);
        quizToDelete.setDeleted(true);
        return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToDelete));

//    quizRepository.delete(quizToDelete);
//    return quizMapper.entityToDto(quizToDelete);
    }

    @Override
    public QuizResponseDto renameQuiz(Long id, String name) {
        if (name.trim().isEmpty()){
            throw new BadRequestException("Please enter all required fields");
        }
        Quiz quiz = getQuiz(id);
        quiz.setName(name);
        return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
    }

    @Override
    public QuestionResponseDto getRandomQuestion(Long id) {
        Quiz quiz = getQuiz(id);
        Question randomQuestion = quiz.getQuestions().get(new Random().nextInt(quiz.getQuestions().size()));
        return questionMapper.entityToDto(randomQuestion);
    }

    @Override
    public QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto) {
        if(questionRequestDto == null || questionRequestDto.getText().trim().isEmpty() || questionRequestDto.getAnswers().isEmpty()){
            throw new BadRequestException("Please enter all required fields");
        }
        Quiz quiz = getQuiz(id);
        Question question = questionMapper.requestDtoToEntity(questionRequestDto);
        for (Answer answer : question.getAnswers()) {
            answer.setQuestion(question);
        }
        question.setDeleted(false);
        question.setQuiz(quiz);
        questionRepository.saveAndFlush(question);
        return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
    }

    @Override
    public QuestionResponseDto deleteQuestion(Long id, Long questionID) {
        Question questionToDelete = getQuestion(questionID);
        questionToDelete.setDeleted(true);
        return questionMapper.entityToDto(questionRepository.saveAndFlush(questionToDelete));

//        questionRepository.delete(questionToDelete);
//        return questionMapper.entityToDto(questionToDelete);
    }

}
