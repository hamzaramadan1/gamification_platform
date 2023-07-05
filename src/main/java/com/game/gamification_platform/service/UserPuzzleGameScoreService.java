package com.game.gamification_platform.service;

import com.game.gamification_platform.model.UserPuzzleGameScore;

import java.util.Optional;

public interface UserPuzzleGameScoreService {
    Optional<UserPuzzleGameScore> findScoreByPuzzleGameId(Long puzzleGameId);

    UserPuzzleGameScore updateScoreByPuzzleGameId(Long puzzleGameId, int updatedScore);

    int getPuzzleGameCount(Long puzzleGameId);
}
