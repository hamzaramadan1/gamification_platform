package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.model.UserMinigameScore;
import com.game.gamification_platform.repository.MinigameRepository;
import com.game.gamification_platform.repository.UserMinigameScoreRepository;
import com.game.gamification_platform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMinigameScoreServiceImpl implements UserMinigameScoreService {

    private final UserRepository userRepository;
    private final UserMinigameScoreRepository userMinigameScoreRepository;

    private final MinigameRepository minigameRepository;
    private UserService userService;

    public UserMinigameScoreServiceImpl(UserRepository userRepository, UserMinigameScoreRepository userMinigameScoreRepository, MinigameRepository minigameRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userMinigameScoreRepository = userMinigameScoreRepository;
        this.minigameRepository = minigameRepository;
        this.userService = userService;
    }

    @Override
    public Optional<UserMinigameScore> findScoreByMinigameId(Long minigameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return userMinigameScoreRepository.findByUserAndMinigameId(user, minigameId);
    }

    @Override
    public UserMinigameScore updateScoreByMinigameId(Long minigameId, int updatedScore) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        Optional<Minigame> optionalMinigame = minigameRepository.findById(minigameId);
        Minigame minigame = optionalMinigame.orElse(null);
        Optional<UserMinigameScore> optionalUpdatedUserMinigameScore = userMinigameScoreRepository.findByUserAndMinigameId(user, minigameId);
        if (optionalUpdatedUserMinigameScore.isPresent()) {
            UserMinigameScore updatedUserMinigameScore = optionalUpdatedUserMinigameScore.get();
            if (updatedUserMinigameScore.getScore() >= updatedScore) {
                return null;
            }
            else {
                int addedPoints = updatedScore - updatedUserMinigameScore.getScore();
                updatedUserMinigameScore.setScore(updatedScore);
                userService.incrementExperiencePoints(user.getUsername(), addedPoints);
                return userMinigameScoreRepository.save(updatedUserMinigameScore);
            }
        } else {
            UserMinigameScore newUserMinigameScore = new UserMinigameScore();
            newUserMinigameScore.setUser(user);
            newUserMinigameScore.setMinigame(minigame);
            newUserMinigameScore.setScore(updatedScore);
            userService.incrementExperiencePoints(user.getUsername(), updatedScore);
            return userMinigameScoreRepository.save(newUserMinigameScore);
        }
    }

    @Override
    public int getMinigameCount(Long minigameId) {
        return userMinigameScoreRepository.countByMinigameId(minigameId);
    }
}
