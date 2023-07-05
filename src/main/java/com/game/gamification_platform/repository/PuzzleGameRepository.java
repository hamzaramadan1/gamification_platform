package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.PuzzleGame;
import com.game.gamification_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PuzzleGameRepository extends JpaRepository<PuzzleGame, Long> {
    List<PuzzleGame> findByCourse(Course course);
    Optional<PuzzleGame> findById(Long puzzleGameId);
    List<PuzzleGame> findByUser(User user);
}
