package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "minigames")
@ToString(exclude = {"questions"})
public class Minigame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "minigame_id")
    private Long id;

    @Column(name = "minigame_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "minigame", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"minigame"}, allowSetters = true)
    private List<Question> questions;

}