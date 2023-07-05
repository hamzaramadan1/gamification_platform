package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Card;

import java.util.List;

public interface CardService {
    Card saveCard(Card card, Long memoryGameId);

    List<Card> findByMemoryGameId(Long memoryGameId);
}
