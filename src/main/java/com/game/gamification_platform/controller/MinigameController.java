package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.service.CourseService;
import com.game.gamification_platform.service.MinigameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/minigame")
public class MinigameController {

    @Autowired
    private MinigameService minigameService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("create/{courseId}")
    public ResponseEntity<?> saveCourse(@RequestBody Minigame minigame, @PathVariable Long courseId) {
        return new ResponseEntity<>(minigameService.saveMinigame(minigame, courseId), HttpStatus.CREATED);
    }
}
