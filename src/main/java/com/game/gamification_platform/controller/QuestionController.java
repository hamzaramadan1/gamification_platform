package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.Question;
import com.game.gamification_platform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PostMapping("create/{minigameId}")
    public ResponseEntity<?> saveQuestion(@RequestBody Question question, @PathVariable Long minigameId) {
        return new ResponseEntity<>(questionService.saveQuestion(question, minigameId), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("{minigameId}")
    public ResponseEntity<?> findByMinigameId(@PathVariable Long minigameId) {
        return ResponseEntity.ok(questionService.findByMinigameId(minigameId));
    }
}
