package com.game.gamification_platform.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userMemoryGameScores")
public class UserMemoryGameScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "memoryGameId")
    private MemoryGame memoryGame;

    @JoinColumn(name = "score")
    private int score;
}
