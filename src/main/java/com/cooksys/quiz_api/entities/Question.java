package com.cooksys.quiz_api.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@NoArgsConstructor
@Data
public class Question {

  @Id
  @GeneratedValue
  private Long id;

  private String text;

  private boolean deleted;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  @OneToMany(mappedBy = "question")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<Answer> answers;

}
