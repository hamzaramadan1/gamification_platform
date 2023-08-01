package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.UserMemoryGameScore;
import com.game.gamification_platform.model.UserMinigameScore;
import com.game.gamification_platform.service.UserMemoryGameScoreService;
import com.game.gamification_platform.service.UserMinigameScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/userMemoryGameScore")
public class UserMemoryGameScoreController {

    @Autowired
    private UserMemoryGameScoreService userMemoryGameScoreService;

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("{memoryGameId}")
    public ResponseEntity<?> findScoreByMemoryGameId(@PathVariable Long memoryGameId) {
        Optional<UserMemoryGameScore> score = userMemoryGameScoreService.findScoreByMemoryGameId(memoryGameId);
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get().getScore());
        }
        else {
            return ResponseEntity.ok(0);
        }
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PutMapping("{memoryGameId}/{updatedScore}")
    public ResponseEntity<?> updateScoreByMemoryGameId(@PathVariable Long memoryGameId, @PathVariable int updatedScore) {
        return new ResponseEntity<>(userMemoryGameScoreService.updateScoreByMemoryGameId(memoryGameId, updatedScore), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("/{memoryGameId}/count")
    public ResponseEntity<Integer> getMemoryGameCount(@PathVariable Long memoryGameId) {
        int count = userMemoryGameScoreService.getMemoryGameCount(memoryGameId);
        return ResponseEntity.ok(count);
    }
}
