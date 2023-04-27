package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.repository.CourseRepository;
import com.game.gamification_platform.repository.MinigameRepository;
import com.game.gamification_platform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinigameServiceImpl implements MinigameService {

    private final MinigameRepository minigameRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private UserService userService;

    public MinigameServiceImpl(MinigameRepository minigameRepository, UserRepository userRepository, CourseRepository courseRepository, UserService userService) {
        this.minigameRepository = minigameRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public List<Minigame> findAllMinigamesForCourse(Long courseId) {
        Course course = courseRepository.getById(courseId);
        return minigameRepository.findByCourse(course);
    }


    @Override
    public void checkAnswer(String userAnswer, Long minigameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        Optional<Minigame> optionalMinigame = minigameRepository.findById(minigameId);
        Minigame minigame = optionalMinigame.orElse(null);
        if (userAnswer.equals(minigame.getAnswer())) {
            userService.incrementExperiencePoints(user.getUsername(), 100);
        }
    }

    @Override
    public Minigame saveMinigame(Minigame minigame, Long courseId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        minigame.setUser(user);
        minigame.setCourse(courseRepository.getById(courseId));
        return minigameRepository.save(minigame);
    }
}
