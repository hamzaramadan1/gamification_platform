package com.game.gamification_platform.repository;

import com.game.gamification_platform.model.Role;
import com.game.gamification_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role = 'USER' ORDER BY u.experiencePoints DESC")
    List<User> findUsersWithHighestExperiencePoints();

    @Modifying
    @Query("update User set role = :role where username = :username")
    void updateUserRole(@Param("username") String email, @Param("role") Role role);
}
