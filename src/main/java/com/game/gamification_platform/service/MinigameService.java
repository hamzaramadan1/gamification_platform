package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.Minigame;

import java.util.List;

public interface MinigameService {

    List<Minigame> findAllMinigamesForCourse(Long courseId);

    void checkAnswer(String userAnswer, Long minigameId);

    Minigame saveMinigame(Minigame minigame, Long courseId);
}
