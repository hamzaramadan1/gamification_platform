package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Minigame;
import com.game.gamification_platform.model.Question;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.repository.MinigameRepository;
import com.game.gamification_platform.repository.QuestionRepository;
import com.game.gamification_platform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final UserRepository userRepository;
    private final MinigameRepository minigameRepository;
    private final QuestionRepository questionRepository;

    private UserService userService;

    public QuestionServiceImpl(UserRepository userRepository, MinigameRepository minigameRepository, QuestionRepository questionRepository, UserService userService) {
        this.userRepository = userRepository;
        this.minigameRepository = minigameRepository;
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    @Override
    public Question saveQuestion(Question question, Long minigameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        question.setUser(user);
        question.setMinigame(minigameRepository.getById(minigameId));
        return questionRepository.save(question);
    }

    @Override
    public List<Question> findByMinigameId(Long minigameId) {
        Minigame minigame = minigameRepository.getById(minigameId);
        return questionRepository.findByMinigame(minigame);
    }
}
