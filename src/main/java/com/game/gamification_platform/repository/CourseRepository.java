package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByUser(User user);
}
