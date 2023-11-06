package com.cooksys.quiz_api.repositories;

import com.cooksys.quiz_api.entities.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByIdAndDeletedFalse(Long id);

    List<Quiz> findALlByDeletedFalse();
  // TODO: Do you need any derived queries? If so add them here.

}
