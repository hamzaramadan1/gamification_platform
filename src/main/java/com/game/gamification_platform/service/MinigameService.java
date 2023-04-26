package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.Minigame;

public interface MinigameService {
    Minigame saveMinigame(Minigame minigame, Long courseId);
}
