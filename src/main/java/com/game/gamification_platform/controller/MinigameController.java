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

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PostMapping("create/{courseId}")
    public ResponseEntity<?> saveMinigame(@RequestBody Minigame minigame, @PathVariable Long courseId) {
        return new ResponseEntity<>(minigameService.saveMinigame(minigame, courseId), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("allForUser")
    public ResponseEntity<?> findAllMinigamesForUser() {
        return ResponseEntity.ok(minigameService.findAllMinigamesForUser());
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("all")
    public ResponseEntity<?> findAllMinigames() {
        return ResponseEntity.ok(minigameService.findAllMinigames());
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("all/count")
    public ResponseEntity<?> findAllMinigamesForUserCount() {
        return ResponseEntity.ok(minigameService.findAllMinigamesForUserCount());
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("all/allCount")
    public ResponseEntity<?> findAllMinigamesCount() {
        return ResponseEntity.ok(minigameService.findAllMinigamesCount());
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteMinigame(@PathVariable Long id) {
        minigameService.deleteMinigame(id);

        return ResponseEntity.ok(true);
    }
}
