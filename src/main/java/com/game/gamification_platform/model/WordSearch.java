package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wordSearches")
@ToString(exclude = {"puzzleGame"})
public class WordSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wordSearch_id")
    private Long id;

    @Column(name = "wordSearch_question", nullable = false)
    private String wordSearchQuestion;

    @ManyToOne
    @JoinColumn(name = "puzzleGame_id")
    @JsonIgnoreProperties(value = {"wordSearches"}, allowSetters = true)
    private PuzzleGame puzzleGame;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "wordSearch_answer", nullable = false)
    private String wordSearchAnswer;
}
