package com.media.chichitube.respositories;

import com.media.chichitube.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean findByEmail(String email);
}
