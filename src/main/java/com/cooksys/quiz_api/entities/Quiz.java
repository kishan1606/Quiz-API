package com.cooksys.quiz_api.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@NoArgsConstructor
@Data
public class Quiz {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private boolean deleted;

  @OneToMany(mappedBy = "quiz")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<Question> questions;

}
