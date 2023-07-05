package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "questions")
@ToString(exclude = {"minigame"})
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "minigame_id")
    @JsonIgnoreProperties(value = {"questions"}, allowSetters = true)
    private Minigame minigame;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "answer_text", nullable = false)
    private String answerText;
}
