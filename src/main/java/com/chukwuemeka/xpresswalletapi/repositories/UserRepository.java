package com.chukwuemeka.xpresswalletapi.repositories;

import com.chukwuemeka.xpresswalletapi.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, String> {
    Optional<User> findByEmailAddress(@NonNull String emailAddress);
}
