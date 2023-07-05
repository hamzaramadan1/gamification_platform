package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "memoryGames")
@ToString(exclude = {"cards"})
public class MemoryGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memoryGameId")
    private Long id;

    @Column(name = "memoryGameDescription")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "memoryGame", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"memoryGame"}, allowSetters = true)
    private List<Card> cards;

}