package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.UserPuzzleGameScore;
import com.game.gamification_platform.service.UserPuzzleGameScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/userPuzzleGameScore")
public class UserPuzzleGameScoreController {
    @Autowired
    private UserPuzzleGameScoreService userPuzzleGameScoreService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("{puzzleGameId}")
    public ResponseEntity<?> findScoreByPuzzleGameId(@PathVariable Long puzzleGameId) {
        Optional<UserPuzzleGameScore> score = userPuzzleGameScoreService.findScoreByPuzzleGameId(puzzleGameId);
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get().getScore());
        }
        else {
            return ResponseEntity.ok(0);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("{puzzleGameId}/{updatedScore}")
    public ResponseEntity<?> updateScoreByPuzzleGameId(@PathVariable Long puzzleGameId, @PathVariable int updatedScore) {
        return new ResponseEntity<>(userPuzzleGameScoreService.updateScoreByPuzzleGameId(puzzleGameId, updatedScore), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{puzzleGameId}/count")
    public ResponseEntity<Integer> getPuzzleGameCount(@PathVariable Long puzzleGameId) {
        int count = userPuzzleGameScoreService.getPuzzleGameCount(puzzleGameId);
        return ResponseEntity.ok(count);
    }
}
