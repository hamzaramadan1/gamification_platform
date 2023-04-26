package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Minigame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinigameRepository extends JpaRepository<Minigame, Long> {
}
