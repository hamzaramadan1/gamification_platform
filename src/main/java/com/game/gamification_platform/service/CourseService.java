package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    Course saveCourse(String courseName, String courseDescription, String courseLink, MultipartFile file) throws IOException;

    Course saveCourseTest(String courseName, String courseDescription, String courseLink);

    List<Course> findAllCoursesForUser();

    List<Course> findAllCourses();

    int findAllCoursesForUserCount();

    int findAllCoursesCount();

    void deleteCourse(Long id);
}
