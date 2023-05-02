package com.game.gamification_platform.service;

import com.game.gamification_platform.model.User;
import com.game.gamification_platform.model.UserMinigameScore;
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

    public UserMinigameScoreServiceImpl(UserRepository userRepository, UserMinigameScoreRepository userMinigameScoreRepository) {
        this.userRepository = userRepository;
        this.userMinigameScoreRepository = userMinigameScoreRepository;
    }

    @Override
    public Optional<UserMinigameScore> findScoreByMinigameId(Long minigameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return userMinigameScoreRepository.findByUserAndMinigameId(user, minigameId);
    }
}
