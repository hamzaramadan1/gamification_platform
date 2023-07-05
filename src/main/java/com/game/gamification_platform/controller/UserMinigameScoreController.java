package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.UserMinigameScore;
import com.game.gamification_platform.service.UserMinigameScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/userminigamescore")
public class UserMinigameScoreController {

    @Autowired
    private UserMinigameScoreService userMinigameScoreService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("{minigameId}")
    public ResponseEntity<?> findScoreByMinigameId(@PathVariable Long minigameId) {
        Optional<UserMinigameScore> score = userMinigameScoreService.findScoreByMinigameId(minigameId);
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get().getScore());
        }
        else {
            return ResponseEntity.ok(0);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("{minigameId}/{updatedScore}")
    public ResponseEntity<?> updateScoreByMinigameId(@PathVariable Long minigameId, @PathVariable int updatedScore) {
        return new ResponseEntity<>(userMinigameScoreService.updateScoreByMinigameId(minigameId, updatedScore), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{minigameId}/count")
    public ResponseEntity<Integer> getMinigameCount(@PathVariable Long minigameId) {
        int count = userMinigameScoreService.getMinigameCount(minigameId);
        return ResponseEntity.ok(count);
    }
}
