package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.MemoryGame;
import com.game.gamification_platform.model.PuzzleGame;
import com.game.gamification_platform.service.MemoryGameService;
import com.game.gamification_platform.service.PuzzleGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/puzzle_game")
public class PuzzleGameController {
    @Autowired
    private PuzzleGameService puzzleGameService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("create/{courseId}")
    public ResponseEntity<?> savePuzzleGame(@RequestBody PuzzleGame puzzleGame, @PathVariable Long courseId) {
        return new ResponseEntity<>(puzzleGameService.savePuzzleGame(puzzleGame, courseId), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("allForUser")
    public ResponseEntity<?> findAllMPuzzleGamesForUser() {
        return ResponseEntity.ok(puzzleGameService.findAllPuzzleGamesForUser());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all")
    public ResponseEntity<?> findAllPuzzleGames() {
        return ResponseEntity.ok(puzzleGameService.findAllPuzzleGames());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all/count")
    public ResponseEntity<?> findAllPuzzleGamesForUserCount() {
        return ResponseEntity.ok(puzzleGameService.findAllPuzzleGamesForUserCount());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all/allCount")
    public ResponseEntity<?> findAllPuzzleGamesCount() {
        return ResponseEntity.ok(puzzleGameService.findAllPuzzleGamesCount());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePuzzleGame(@PathVariable Long id) {
        puzzleGameService.deletePuzzleGame(id);

        return ResponseEntity.ok(true);
    }
}
