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

    @Column(name = "minigame_description", nullable = false)
    private String description;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}