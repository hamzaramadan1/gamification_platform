package com.game.gamification_platform.service;

import com.game.gamification_platform.model.MemoryGame;

import java.util.List;

public interface MemoryGameService {
    List<MemoryGame> findAllMemoryGamesForCourse(Long courseId);

    MemoryGame saveMemoryGame(MemoryGame memoryGame, Long courseId);

    List<MemoryGame> findAllMemoryGamesForUser();

    List<MemoryGame> findAllMemoryGames();

    int findAllMemoryGamesForUserCount();

    int findAllMemoryGamesCount();

    void deleteMemoryGame(Long id);
}
