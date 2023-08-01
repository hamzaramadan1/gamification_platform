package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.User;
import com.game.gamification_platform.security.UserPrincipal;
import com.game.gamification_platform.service.AuthenticationService;
import com.game.gamification_platform.service.JwtRefreshTokenService;
import com.game.gamification_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @CrossOrigin(origins = "http://192.168.1.64:8036/")
    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036/")
    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        User userPrincipal = authenticationService.signInAndReturnJWT(user);
        if (userPrincipal == null) {
            throw new UsernameNotFoundException("User not found with username: " + user.getUsername());
        }
        return new ResponseEntity<>(userPrincipal,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036/")
    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String token) {
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
    }
}
