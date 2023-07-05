package com.game.gamification_platform.service;

import com.game.gamification_platform.model.UserMemoryGameScore;

import java.util.Optional;

public interface UserMemoryGameScoreService {
    Optional<UserMemoryGameScore> findScoreByMemoryGameId(Long memoryGameId);

    UserMemoryGameScore updateScoreByMemoryGameId(Long memoryGameId, int updatedScore);

    int getMemoryGameCount(Long memoryGameId);
}
