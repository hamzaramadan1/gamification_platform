package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.User;
import com.game.gamification_platform.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MinigameService minigameService;

    @Autowired
    private MemoryGameService memoryGameService;

    @Autowired
    private PuzzleGameService puzzleGameService;

    @CrossOrigin(origins = "http://localhost:4200")
        @PutMapping("user/increment-experience-points/{username}/{experiencePoints}")
    public ResponseEntity<?> incrementExperiencePoints(@PathVariable String username, @PathVariable int experiencePoints) {
        try {
            userService.incrementExperiencePoints(username, experiencePoints);
            return ResponseEntity.ok("Experience points incremented successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error incrementing experience points");
        }
    }

    /*@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("user/answer/{userAnswer}/{minigameId}")
    public ResponseEntity<?> checkAnswer(@PathVariable String userAnswer, @PathVariable Long minigameId) {
        try {
            minigameService.checkAnswer(userAnswer, minigameId);
            return ResponseEntity.ok("{\"result\": \"Right answer\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    */

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("update/updateProfilePic")
    public ResponseEntity<?> updateProfilePic(@RequestParam("userImageFile") MultipartFile userImageFile) throws IOException {
        return new ResponseEntity<>(userService.updateProfilePic(userImageFile), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("update/{currentPassword}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String currentPassword) {
        try {
            return new ResponseEntity<>(userService.updateUser(user, currentPassword), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/getFirstThreeUsersWithHighestExperiencePoints")
    public List<User> getFirstThreeUsersWithHighestExperiencePoints() {
        return userService.getFirstThreeUsersWithHighestExperiencePoints();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/getUsersWithHighestExperiencePoints")
    public List<User> getUsersWithHighestExperiencePoints() {
        return userService.getUsersWithHighestExperiencePoints();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/courses/all")
    public ResponseEntity<?> findAllCourses() {
        return ResponseEntity.ok(courseService.findAllCourses());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/courses/allCount")
    public ResponseEntity<?> findAllCoursesCount() {
        return ResponseEntity.ok(courseService.findAllCoursesCount());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/minigames/{courseId}")
    public ResponseEntity<?> findAllMinigamesForCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(minigameService.findAllMinigamesForCourse(courseId));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/memoryGames/{courseId}")
    public ResponseEntity<?> findAllMemoryGamesForCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(memoryGameService.findAllMemoryGamesForCourse(courseId));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/puzzleGames/{courseId}")
    public ResponseEntity<?> findAllPuzzleGamesForCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(puzzleGameService.findAllPuzzleGamesForCourse(courseId));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("admin/allUsers")
    public ResponseEntity<?> findAllUsersUsersCount() {
        return ResponseEntity.ok(userService.findAllUsersUsersCount());
    }
}
