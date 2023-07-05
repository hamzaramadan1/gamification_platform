package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.MemoryGame;
import com.game.gamification_platform.service.MemoryGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/memory_game")
public class MemoryGameController {

    @Autowired
    private MemoryGameService memoryGameService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("create/{courseId}")
    public ResponseEntity<?> saveMemoryGame(@RequestBody MemoryGame memoryGame, @PathVariable Long courseId) {
        return new ResponseEntity<>(memoryGameService.saveMemoryGame(memoryGame, courseId), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("allForUser")
    public ResponseEntity<?> findAllMemoryGamesForUser() {
        return ResponseEntity.ok(memoryGameService.findAllMemoryGamesForUser());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all")
    public ResponseEntity<?> findAllMemoryGames() {
        return ResponseEntity.ok(memoryGameService.findAllMemoryGames());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all/count")
    public ResponseEntity<?> findAllMemoryGamesForUserCount() {
        return ResponseEntity.ok(memoryGameService.findAllMemoryGamesForUserCount());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all/allCount")
    public ResponseEntity<?> findAllMemoryGamesCount() {
        return ResponseEntity.ok(memoryGameService.findAllMemoryGamesCount());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteMemoryGame(@PathVariable Long id) {
        memoryGameService.deleteMemoryGame(id);

        return ResponseEntity.ok(true);
    }
}