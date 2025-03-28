package com.example.senior_project.repository;

import com.example.senior_project.model.User;
import com.example.senior_project.model.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailVerificationToken(String token);

    List<User> findByUserType(UserType userType);

    Page<User> findByUserType(UserType userType, Pageable pageable);

    default Page<User> findByRole(String role, Pageable pageable) {
        return findByUserType(UserType.valueOf(role), pageable);
    }

    Page<User> findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            String firstName, String lastName, String email, Pageable pageable);

    Page<User> findByUserTypeAndFirstNameContainingOrLastNameContainingOrEmailContaining(
            UserType userType, String firstName, String lastName, String email, Pageable pageable);
}