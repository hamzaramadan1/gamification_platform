package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Grade;
import com.game.gamification_platform.model.Role;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.repository.UserRepository;
import com.game.gamification_platform.utils.ImageUtils;
import javassist.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        if (user.getUsername() != null) {
            User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
            if (existingUser != null) {
                user.setRole(existingUser.getRole());
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRole(Role.USER);
                user.setGrade(Grade.Débutant);
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User updateProfilePic(MultipartFile userImageFile) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User updatedUser = optionalUser.orElse(null);
        String fileName = StringUtils.cleanPath(userImageFile.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(fileName);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        String uploadDir = "C:\\Users\\hramadan\\angular-gamification_platform\\src\\assets\\user_images";
        Path uploadPath = Paths.get(uploadDir);
        if (!userImageFile.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(userImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        updatedUser.setUserImageFile(ImageUtils.compressImage(userImageFile.getBytes()));
        updatedUser.setUserImageName(newFileName);
        updatedUser.setUserImageType(userImageFile.getContentType());
        return userRepository.save(updatedUser);
    }

    @Override
    public User updateUser(User user, String currentPassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User updatedUser = optionalUser.orElse(null);
        if (currentPassword != null) {
            passwordEncoder.encode(currentPassword);
            if (passwordEncoder.matches(currentPassword, updatedUser.getPassword())) {
                if (user.getUsername() == null || user.getUsername().isEmpty()) {
                    user.setUsername(updatedUser.getUsername()); // retain existing username
                }
                updatedUser.setUsername(user.getUsername());
                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    user.setPassword(updatedUser.getPassword()); // retain existing username
                }
                else {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
                updatedUser.setPassword(user.getPassword());
                if (user.getEmail() == null || user.getEmail().isEmpty()) {
                    user.setEmail(updatedUser.getEmail()); // retain existing username
                }
                updatedUser.setEmail(user.getEmail());
                if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
                    user.setFirstName(updatedUser.getFirstName()); // retain existing username
                }
                updatedUser.setFirstName(user.getFirstName());
                if (user.getLastName() == null || user.getLastName().isEmpty()) {
                    user.setLastName(updatedUser.getLastName()); // retain existing username
                }
                updatedUser.setLastName(user.getLastName());
            }
            else {
                throw new IllegalArgumentException("Passwords do not match");
            }
        }
        return userRepository.save(updatedUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getFirstThreeUsersWithHighestExperiencePoints() {
        List<User> allUsers = userRepository.findUsersWithHighestExperiencePoints();
        if (allUsers.size() > 3) {
            return allUsers.subList(0, 3);
        } else {
            return allUsers;
        }
    }

    @Override
    public List<User> getUsersWithHighestExperiencePoints() {
        List<User> allUsers = userRepository.findUsersWithHighestExperiencePoints();
        return allUsers;
    }

    @Override
    @Transactional
    public void changeRole(Role newRole, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        try {
            user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        user.setRole(newRole);
        if (newRole == Role.ADMIN) {
            user.setGrade(Grade.Professeur);
        } else if (newRole == Role.USER) {
            if (user.getUserLevel() >= 1 && user.getUserLevel() < 25) {
                user.setGrade(Grade.Débutant);
            }
            else if (user.getUserLevel() >= 25 && user.getUserLevel() < 50) {
                user.setGrade(Grade.Intermédiaire);
            }
            else if (user.getUserLevel() >= 50 && user.getUserLevel() < 75) {
                user.setGrade(Grade.Avancé);
            }
            else if (user.getUserLevel() >= 75) {
                user.setGrade(Grade.Expert);
            }
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void lockUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        try {
            user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        user.setLocked(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void unlockUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        try {
            user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        user.setLocked(false);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        try {
            user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public int findAllUsersUsersCount() {
        List<User> users = userRepository.findByRole(Role.USER);
        return users.size();
    }

    @Override
    public void calculateUserLevel(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = null;
        try {
            user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        int requiredPoints = ((user.getUserLevel() * 1000) + (user.getUserLevel() * 100 * user.getUserLevel()));
        if (user.getExperiencePoints() >= requiredPoints) {
            user.setUserLevel(user.getUserLevel()+1);
            if (user.getUserLevel() >= 1 && user.getUserLevel() < 25) {
                user.setGrade(Grade.Débutant);
            }
            else if (user.getUserLevel() >= 25 && user.getUserLevel() < 50) {
                user.setGrade(Grade.Intermédiaire);
            }
            else if (user.getUserLevel() >= 50 && user.getUserLevel() < 75) {
                user.setGrade(Grade.Avancé);
            }
            else if (user.getUserLevel() >= 75) {
                user.setGrade(Grade.Expert);
            }
        }
    }

    @Override
    public void incrementExperiencePoints(String username, int points) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = null;
        try {
            user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        user.setExperiencePoints(user.getExperiencePoints() + points);
        calculateUserLevel(user.getUsername());
        userRepository.save(user);
    }
}
