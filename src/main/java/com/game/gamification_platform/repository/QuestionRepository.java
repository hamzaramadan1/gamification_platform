package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByMinigame(Minigame minigame);
}
