package com.game.gamification_platform.controller;

import com.game.gamification_platform.model.Role;
import com.game.gamification_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/superadmin")
public class SuperadminController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @GetMapping("all")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PutMapping("user/{id}/change/{role}")
    public ResponseEntity<?> changeRole(@PathVariable Long id,
                                        @PathVariable Role role) {
        userService.changeRole(role, id);

        return ResponseEntity.ok(true);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PostMapping("lock/{id}")
    public ResponseEntity<?> lockUser(@PathVariable Long id) {
        userService.lockUser(id);

        return ResponseEntity.ok(true);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @PostMapping("unlock/{id}")
    public ResponseEntity<?> unlockUser(@PathVariable Long id) {
        userService.unlockUser(id);

        return ResponseEntity.ok(true);
    }

    @CrossOrigin(origins = "http://192.168.1.64:8036")
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok(true);
    }
}
