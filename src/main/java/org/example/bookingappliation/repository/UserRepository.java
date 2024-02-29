package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
