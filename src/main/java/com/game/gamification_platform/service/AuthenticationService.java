package com.game.gamification_platform.service;

import com.game.gamification_platform.model.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
