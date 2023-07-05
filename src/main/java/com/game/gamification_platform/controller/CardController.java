package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Card;
import com.game.gamification_platform.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("create/{memoryGameId}")
    public ResponseEntity<?> saveCard(@RequestBody Card card, @PathVariable Long memoryGameId) {
        return new ResponseEntity<>(cardService.saveCard(card, memoryGameId), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("{memoryGameId}")
    public ResponseEntity<?> findByMemoryGameId(@PathVariable Long memoryGameId) {
        return ResponseEntity.ok(cardService.findByMemoryGameId(memoryGameId));
    }
}