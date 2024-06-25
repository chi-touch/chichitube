package com.media.chichitube.respositories;

import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select  u from User u where u.email =:email")
    Optional<User> findByEmail(String email) throws UserNotFoundException;
//    boolean findByEmail(String email);
}
