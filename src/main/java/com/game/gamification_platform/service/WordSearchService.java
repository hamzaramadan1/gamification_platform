package com.game.gamification_platform.service;

import com.game.gamification_platform.model.WordSearch;

import java.util.List;

public interface WordSearchService {
    WordSearch saveWordSearch(WordSearch wordSearch, Long puzzleGameId);

    List<WordSearch> findByPuzzleGameId(Long puzzleGameId);
}
