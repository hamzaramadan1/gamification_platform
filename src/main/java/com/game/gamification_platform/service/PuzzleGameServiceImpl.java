package com.game.gamification_platform.service;


import com.game.gamification_platform.model.*;
import com.game.gamification_platform.repository.CourseRepository;
import com.game.gamification_platform.repository.PuzzleGameRepository;
import com.game.gamification_platform.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuzzleGameServiceImpl implements PuzzleGameService {
    private final PuzzleGameRepository puzzleGameRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private UserService userService;

    public PuzzleGameServiceImpl(PuzzleGameRepository puzzleGameRepository, UserRepository userRepository, CourseRepository courseRepository, UserService userService) {
        this.puzzleGameRepository = puzzleGameRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public List<PuzzleGame> findAllPuzzleGamesForCourse(Long courseId) {
        Course course = courseRepository.getById(courseId);
        return puzzleGameRepository.findByCourse(course);
    }

    @Override
    public PuzzleGame savePuzzleGame(PuzzleGame puzzleGame, Long courseId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        for (WordSearch wordSearch : puzzleGame.getWordSearches()) {
            wordSearch.setPuzzleGame(puzzleGame);
            wordSearch.setUser(user);
        }
        puzzleGame.setUser(user);
        puzzleGame.setCourse(courseRepository.getById(courseId));
        return puzzleGameRepository.save(puzzleGame);
    }

    @Override
    public List<PuzzleGame> findAllPuzzleGamesForUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return puzzleGameRepository.findByUser(user);
    }

    @Override
    public List<PuzzleGame> findAllPuzzleGames() {
        return puzzleGameRepository.findAll();
    }

    @Override
    public int findAllPuzzleGamesForUserCount() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        List<PuzzleGame> puzzleGames =  puzzleGameRepository.findByUser(user);
        return puzzleGames.size();
    }

    @Override
    public int findAllPuzzleGamesCount() {
        List<PuzzleGame> puzzleGames =  puzzleGameRepository.findAll();
        return puzzleGames.size();
    }

    @Override
    public void deletePuzzleGame(Long id) {
        Optional<PuzzleGame> optionalPuzzleGame = puzzleGameRepository.findById(id);
        PuzzleGame puzzleGame = null;
        try {
            puzzleGame = optionalPuzzleGame.orElseThrow(() -> new NotFoundException("Puzzle Game not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        puzzleGameRepository.delete(puzzleGame);
    }
}
