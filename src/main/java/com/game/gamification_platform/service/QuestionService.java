package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Question;

import java.util.List;

public interface QuestionService {
    Question saveQuestion(Question question, Long minigameId);

    List<Question> findByMinigameId(Long minigameId);
}
