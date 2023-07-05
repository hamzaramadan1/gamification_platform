package com.game.gamification_platform.service;

import com.game.gamification_platform.model.*;
import com.game.gamification_platform.repository.PuzzleGameRepository;
import com.game.gamification_platform.repository.UserRepository;
import com.game.gamification_platform.repository.WordSearchRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordSearchServiceImpl implements WordSearchService {
    private final UserRepository userRepository;
    private final PuzzleGameRepository puzzleGameRepository;
    private final WordSearchRepository wordSearchRepository;
    private UserService userService;

    public WordSearchServiceImpl(UserRepository userRepository, PuzzleGameRepository puzzleGameRepository, WordSearchRepository wordSearchRepository, UserService userService) {
        this.userRepository = userRepository;
        this.puzzleGameRepository = puzzleGameRepository;
        this.wordSearchRepository = wordSearchRepository;
        this.userService = userService;
    }

    @Override
    public WordSearch saveWordSearch(WordSearch wordSearch, Long puzzleGameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        wordSearch.setUser(user);
        wordSearch.setPuzzleGame(puzzleGameRepository.getById(puzzleGameId));
        return wordSearchRepository.save(wordSearch);
    }

    @Override
    public List<WordSearch> findByPuzzleGameId(Long puzzleGameId) {
        PuzzleGame puzzleGame = puzzleGameRepository.getById(puzzleGameId);
        return wordSearchRepository.findByPuzzleGame(puzzleGame);
    }
}
