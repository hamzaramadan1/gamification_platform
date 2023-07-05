package com.game.gamification_platform.service;

import com.game.gamification_platform.model.MemoryGame;
import com.game.gamification_platform.model.Card;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.repository.MemoryGameRepository;
import com.game.gamification_platform.repository.CardRepository;
import com.game.gamification_platform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {
    private final UserRepository userRepository;
    private final MemoryGameRepository memoryGameRepository;
    private final CardRepository cardRepository;

    private UserService userService;

    public CardServiceImpl(UserRepository userRepository, MemoryGameRepository memoryGameRepository, CardRepository cardRepository, UserService userService) {
        this.userRepository = userRepository;
        this.memoryGameRepository = memoryGameRepository;
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    @Override
    public Card saveCard(Card card, Long memoryGameId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        card.setUser(user);
        card.setMemoryGame(memoryGameRepository.getById(memoryGameId));
        return cardRepository.save(card);
    }

    @Override
    public List<Card> findByMemoryGameId(Long memoryGameId) {
        MemoryGame memoryGame = memoryGameRepository.getById(memoryGameId);
        return cardRepository.findByMemoryGame(memoryGame);
    }
}
