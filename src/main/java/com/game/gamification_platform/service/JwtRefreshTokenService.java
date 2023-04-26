package com.game.gamification_platform.service;

import com.game.gamification_platform.model.JwtRefreshToken;
import com.game.gamification_platform.model.User;

public interface JwtRefreshTokenService {
    JwtRefreshToken createRefreshToken(Long userId);

    User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
