package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cards")
@ToString(exclude = {"memoryGame"})
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "card_question", nullable = false)
    private String cardQuestion;

    @ManyToOne
    @JoinColumn(name = "memoryGameId")
    @JsonIgnoreProperties(value = {"cards"}, allowSetters = true)
    private MemoryGame memoryGame;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "card_answer", nullable = false)
    private String cardAnswer;
}