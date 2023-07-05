package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.service.CourseService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("allForUser")
    public ResponseEntity<?> findAllCoursesForUser() {
        return ResponseEntity.ok(courseService.findAllCoursesForUser());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("all/count")
    public ResponseEntity<?> findAllCoursesForUserCount() {
        return ResponseEntity.ok(courseService.findAllCoursesForUserCount());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("create")
    public ResponseEntity<?> saveCourse(@RequestParam("courseName") String courseName,
                                        @RequestParam("courseDescription") String courseDescription,
                                        @RequestParam("courseLink") String courseLink,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(courseService.saveCourse(courseName, courseDescription, courseLink, file), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "createTest")
    public ResponseEntity<?> saveCourseTest(@RequestParam("courseName") String courseName,
                                            @RequestParam("courseDescription") String courseDescription,
                                            @RequestParam("courseLink") String courseLink){
        return new ResponseEntity<>(courseService.saveCourseTest(courseName, courseDescription, courseLink), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);

        return ResponseEntity.ok(true);
    }

}
