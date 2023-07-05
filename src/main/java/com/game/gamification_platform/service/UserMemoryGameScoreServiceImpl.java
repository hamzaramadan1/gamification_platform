package com.game.gamification_platform.service;

import com.game.gamification_platform.model.*;
import com.game.gamification_platform.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMemoryGameScoreServiceImpl implements UserMemoryGameScoreService {
    private final UserRepository userRepository;
    private final UserMemoryGameScoreRepository userMemoryGameScoreRepository;

    private final MemoryGameRepository memoryGameRepository;
    private UserService userService;

    public UserMemoryGameScoreServiceImpl(UserRepository userRepository, UserMemoryGameScoreRepository userMemoryGameScoreRepository, MemoryGameRepository memoryGameRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userMemoryGameScoreRepository = userMemoryGameScoreRepository;
        this.memoryGameRepository = memoryGameRepository;
        this.userService = userService;
    }

    @Override
    public Optional<UserMemoryGameScore> findScoreByMemoryGameId(Long memoryGameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return userMemoryGameScoreRepository.findByUserAndMemoryGameId(user, memoryGameId);
    }

    @Override
    public UserMemoryGameScore updateScoreByMemoryGameId(Long memoryGameId, int updatedScore) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        Optional<MemoryGame> optionalMemoryGame = memoryGameRepository.findById(memoryGameId);
        MemoryGame memoryGame = optionalMemoryGame.orElse(null);
        Optional<UserMemoryGameScore> optionalUpdatedUserMemoryGameScore = userMemoryGameScoreRepository.findByUserAndMemoryGameId(user, memoryGameId);
        if (optionalUpdatedUserMemoryGameScore.isPresent()) {
            UserMemoryGameScore updatedUserMemoryGameScore = optionalUpdatedUserMemoryGameScore.get();
            if (updatedUserMemoryGameScore.getScore() >= updatedScore) {
                return null;
            }
            else {
                int addedPoints = updatedScore - updatedUserMemoryGameScore.getScore();
                updatedUserMemoryGameScore.setScore(updatedScore);
                userService.incrementExperiencePoints(user.getUsername(), addedPoints);
                return userMemoryGameScoreRepository.save(updatedUserMemoryGameScore);
            }
        } else {
            UserMemoryGameScore newUserMemoryGameScore = new UserMemoryGameScore();
            newUserMemoryGameScore.setUser(user);
            newUserMemoryGameScore.setMemoryGame(memoryGame);
            newUserMemoryGameScore.setScore(updatedScore);
            userService.incrementExperiencePoints(user.getUsername(), updatedScore);
            return userMemoryGameScoreRepository.save(newUserMemoryGameScore);
        }
    }

    @Override
    public int getMemoryGameCount(Long memoryGameId) {
        return userMemoryGameScoreRepository.countByMemoryGameId(memoryGameId);
    }
}
