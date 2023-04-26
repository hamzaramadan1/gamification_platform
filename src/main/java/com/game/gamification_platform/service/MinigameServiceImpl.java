package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.repository.CourseRepository;
import com.game.gamification_platform.repository.MinigameRepository;
import com.game.gamification_platform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MinigameServiceImpl implements MinigameService {

    private final MinigameRepository minigameRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public MinigameServiceImpl(MinigameRepository minigameRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.minigameRepository = minigameRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
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
