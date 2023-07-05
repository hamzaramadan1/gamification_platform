package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.PuzzleGame;
import com.game.gamification_platform.model.WordSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordSearchRepository extends JpaRepository<WordSearch, Long> {
    List<WordSearch> findByPuzzleGame(PuzzleGame puzzleGame);
}
