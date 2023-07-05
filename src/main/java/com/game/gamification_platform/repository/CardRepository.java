package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Card;
import com.game.gamification_platform.model.MemoryGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByMemoryGame(MemoryGame memoryGame);
}