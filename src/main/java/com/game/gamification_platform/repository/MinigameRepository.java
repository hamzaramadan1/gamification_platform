package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MinigameRepository extends JpaRepository<Minigame, Long> {
    List<Minigame> findByCourse(Course course);
    Optional<Minigame> findById(Long minigameId);
    List<Minigame> findByUser(User user);
}
