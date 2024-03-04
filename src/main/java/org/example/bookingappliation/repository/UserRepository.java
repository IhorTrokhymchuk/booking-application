package org.example.bookingappliation.repository;

import java.util.Optional;
import org.example.bookingappliation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
