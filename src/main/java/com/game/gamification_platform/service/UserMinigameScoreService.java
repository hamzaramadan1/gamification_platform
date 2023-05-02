package com.game.gamification_platform.service;

import com.game.gamification_platform.model.UserMinigameScore;

import java.util.Optional;

public interface UserMinigameScoreService {
    Optional<UserMinigameScore> findScoreByMinigameId(Long minigameId);
}
