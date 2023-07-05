package com.game.gamification_platform.service;

import com.game.gamification_platform.model.*;
import com.game.gamification_platform.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPuzzleGameScoreServiceImpl implements UserPuzzleGameScoreService {
    private final UserRepository userRepository;
    private final UserPuzzleGameScoreRepository userPuzzleGameScoreRepository;

    private final PuzzleGameRepository puzzleGameRepository;
    private UserService userService;

    public UserPuzzleGameScoreServiceImpl(UserRepository userRepository, UserPuzzleGameScoreRepository userPuzzleGameScoreRepository, PuzzleGameRepository puzzleGameRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userPuzzleGameScoreRepository = userPuzzleGameScoreRepository;
        this.puzzleGameRepository = puzzleGameRepository;
        this.userService = userService;
    }

    @Override
    public Optional<UserPuzzleGameScore> findScoreByPuzzleGameId(Long puzzleGameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return userPuzzleGameScoreRepository.findByUserAndPuzzleGameId(user, puzzleGameId);
    }

    @Override
    public UserPuzzleGameScore updateScoreByPuzzleGameId(Long puzzleGameId, int updatedScore) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        Optional<PuzzleGame> optionalPuzzleGame = puzzleGameRepository.findById(puzzleGameId);
        PuzzleGame puzzleGame = optionalPuzzleGame.orElse(null);
        Optional<UserPuzzleGameScore> optionalUpdatedUserPuzzleGameScore = userPuzzleGameScoreRepository.findByUserAndPuzzleGameId(user, puzzleGameId);
        if (optionalUpdatedUserPuzzleGameScore.isPresent()) {
            UserPuzzleGameScore updatedUserPuzzleGameScore = optionalUpdatedUserPuzzleGameScore.get();
            if (updatedUserPuzzleGameScore.getScore() >= updatedScore) {
                return null;
            }
            else {
                int addedPoints = updatedScore - updatedUserPuzzleGameScore.getScore();
                updatedUserPuzzleGameScore.setScore(updatedScore);
                userService.incrementExperiencePoints(user.getUsername(), addedPoints);
                return userPuzzleGameScoreRepository.save(updatedUserPuzzleGameScore);
            }
        } else {
            UserPuzzleGameScore newUserPuzzleGameScore = new UserPuzzleGameScore();
            newUserPuzzleGameScore.setUser(user);
            newUserPuzzleGameScore.setPuzzleGame(puzzleGame);
            newUserPuzzleGameScore.setScore(updatedScore);
            userService.incrementExperiencePoints(user.getUsername(), updatedScore);
            return userPuzzleGameScoreRepository.save(newUserPuzzleGameScore);
        }
    }

    @Override
    public int getPuzzleGameCount(Long puzzleGameId) {
        return userPuzzleGameScoreRepository.countByPuzzleGameId(puzzleGameId);
    }
}
