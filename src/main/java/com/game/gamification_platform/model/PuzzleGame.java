package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "puzzleGames")
@ToString(exclude = {"wordSearches"})
public class PuzzleGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "puzzleGameId")
    private Long id;

    @Column(name = "puzzleGameDescription")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "puzzleGame", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"puzzleGame"}, allowSetters = true)
    private List<WordSearch> wordSearches;
}
