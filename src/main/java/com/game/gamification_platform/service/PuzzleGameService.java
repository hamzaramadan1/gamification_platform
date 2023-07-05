package com.game.gamification_platform.service;

import com.game.gamification_platform.model.PuzzleGame;

import java.util.List;

public interface PuzzleGameService {
    List<PuzzleGame> findAllPuzzleGamesForCourse(Long courseId);

    PuzzleGame savePuzzleGame(PuzzleGame puzzleGame, Long courseId);

    List<PuzzleGame> findAllPuzzleGamesForUser();

    List<PuzzleGame> findAllPuzzleGames();

    int findAllPuzzleGamesForUserCount();

    int findAllPuzzleGamesCount();

    void deletePuzzleGame(Long id);
}
