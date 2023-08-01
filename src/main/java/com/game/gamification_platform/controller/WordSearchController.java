package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Card;
import com.game.gamification_platform.model.WordSearch;
import com.game.gamification_platform.service.CardService;
import com.game.gamification_platform.service.WordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/word_search")
public class WordSearchController {
    @Autowired
    private WordSearchService wordSearchService;

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PostMapping("create/{memoryGameId}")
    public ResponseEntity<?> saveWordSearch(@RequestBody WordSearch wordSearch, @PathVariable Long puzzleGameId) {
        return new ResponseEntity<>(wordSearchService.saveWordSearch(wordSearch, puzzleGameId), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("{puzzleGameId}")
    public ResponseEntity<?> findByPuzzleGameId(@PathVariable Long puzzleGameId) {
        return ResponseEntity.ok(wordSearchService.findByPuzzleGameId(puzzleGameId));
    }
}
