package com.game.gamification_platform.service;

import com.game.gamification_platform.model.*;
import com.game.gamification_platform.repository.*;
import javassist.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoryGameServiceImpl implements MemoryGameService {
    private final MemoryGameRepository memoryGameRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private UserService userService;

    public MemoryGameServiceImpl(MemoryGameRepository memoryGameRepository, UserRepository userRepository, CourseRepository courseRepository, UserService userService) {
        this.memoryGameRepository = memoryGameRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public List<MemoryGame> findAllMemoryGamesForCourse(Long courseId) {
        Course course = courseRepository.getById(courseId);
        return memoryGameRepository.findByCourse(course);
    }

    @Override
    public MemoryGame saveMemoryGame(MemoryGame memoryGame, Long courseId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        for (Card card : memoryGame.getCards()) {
            card.setMemoryGame(memoryGame);
            card.setUser(user);
        }
        memoryGame.setUser(user);
        memoryGame.setCourse(courseRepository.getById(courseId));
        return memoryGameRepository.save(memoryGame);
    }

    @Override
    public List<MemoryGame> findAllMemoryGamesForUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return memoryGameRepository.findByUser(user);
    }

    @Override
    public List<MemoryGame> findAllMemoryGames() {
        return memoryGameRepository.findAll();
    }
    @Override
    public int findAllMemoryGamesForUserCount() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        List<MemoryGame> memoryGames =  memoryGameRepository.findByUser(user);
        return memoryGames.size();
    }

    @Override
    public int findAllMemoryGamesCount() {
        List<MemoryGame> memoryGames =  memoryGameRepository.findAll();
        return memoryGames.size();
    }

    @Override
    public void deleteMemoryGame(Long id) {
        Optional<MemoryGame> optionalMemoryGame = memoryGameRepository.findById(id);
        MemoryGame memoryGame = null;
        try {
            memoryGame = optionalMemoryGame.orElseThrow(() -> new NotFoundException("Memory Game not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        memoryGameRepository.delete(memoryGame);
    }
}
