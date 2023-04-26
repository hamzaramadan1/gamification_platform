package com.game.gamification_platform.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "minigames")
public class Minigame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "minigame_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "minigame_name", nullable = false)
    private MinigameType minigameType;

    @Column(name = "minigame_description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}