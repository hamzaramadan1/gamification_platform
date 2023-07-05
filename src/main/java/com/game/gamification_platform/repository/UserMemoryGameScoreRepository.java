package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMemoryGameScoreRepository extends JpaRepository<UserMemoryGameScore, Long> {
    UserMemoryGameScore findByUserAndMemoryGame(User user, MemoryGame memoryGame);
    Optional<UserMemoryGameScore> findByUserAndMemoryGameId(User scoreUser, Long memoryGameId);
    int countByMemoryGameId(Long memoryGameId);

}
