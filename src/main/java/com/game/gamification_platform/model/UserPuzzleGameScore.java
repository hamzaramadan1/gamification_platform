package com.game.gamification_platform.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userPuzzleGameScores")
public class UserPuzzleGameScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "puzzleGameId")
    private PuzzleGame puzzleGame;

    @JoinColumn(name = "score")
    private int score;
}
