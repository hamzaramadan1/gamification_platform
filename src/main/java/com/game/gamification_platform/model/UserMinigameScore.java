package com.game.gamification_platform.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userminigamescores")
public class UserMinigameScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "minigame_id")
    private Minigame minigame;

    private int score;
}
