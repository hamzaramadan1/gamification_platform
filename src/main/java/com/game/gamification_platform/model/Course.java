package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
@ToString(exclude = {"user", "minigames", "memoryGames", "puzzleGames"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_description", nullable = false)
    private String courseDescription;

    @Column(name = "course_link", nullable = false)
    private String courseLink;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(name = "file", length = 1000)
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    @JsonIgnore
    private List<Minigame> minigames;

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    @JsonIgnore
    private List<MemoryGame> memoryGames;

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    @JsonIgnore
    private List<PuzzleGame> puzzleGames;
}