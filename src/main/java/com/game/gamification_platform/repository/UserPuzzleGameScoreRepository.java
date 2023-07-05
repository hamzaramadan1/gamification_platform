package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPuzzleGameScoreRepository extends JpaRepository<UserPuzzleGameScore, Long> {
    UserPuzzleGameScore findByUserAndPuzzleGame(User user, PuzzleGame puzzleGame);
    Optional<UserPuzzleGameScore> findByUserAndPuzzleGameId(User scoreUser, Long puzzleGameId);
    int countByPuzzleGameId(Long puzzleGameId);

}
