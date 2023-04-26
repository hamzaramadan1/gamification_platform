package com.game.gamification_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "userImageName")
    private String userImageName;

    @Column(name = "userImageType")
    private String userImageType;

    @Lob
    @Column(name = "userImageFile", length = 1000)
    private byte[] userImageFile;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Course> courses;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Minigame> minigames;

    @Column(name = "locked", nullable = false)
    private boolean locked;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "experience_points", nullable = false)
    private int experiencePoints = 0;

    @Column(name = "user_level", nullable = false)
    private int userLevel = 1;

    @Transient
    private String accessToken;

    @Transient
    private String refreshToken;
}
