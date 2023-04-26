package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Role;
import com.game.gamification_platform.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User updateProfilePic(MultipartFile userImageFile) throws IOException;

    User updateUser(User user, String currentPassword);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, Long id);

    @Transactional
    void lockUser(Long id);

    @Transactional
    void unlockUser(Long id);

    void deleteUser(Long id);

    List<User> findAllUsers();

    int findAllUsersUsersCount();

    void calculateUserLevel(String username);

    void incrementExperiencePoints(String username, int points);
}
