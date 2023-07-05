package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.model.UserMinigameScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMinigameScoreRepository extends JpaRepository<UserMinigameScore, Long> {
    UserMinigameScore findByUserAndMinigame(User user, Minigame minigame);
    Optional<UserMinigameScore> findByUserAndMinigameId(User scoreUser, Long minigameId);
    int countByMinigameId(Long minigameId);
}
