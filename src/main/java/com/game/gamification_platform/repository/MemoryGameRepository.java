package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.MemoryGame;
import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoryGameRepository extends JpaRepository<MemoryGame, Long> {
    List<MemoryGame> findByCourse(Course course);
    Optional<MemoryGame> findById(Long memoryGameId);
    List<MemoryGame> findByUser(User user);
}
