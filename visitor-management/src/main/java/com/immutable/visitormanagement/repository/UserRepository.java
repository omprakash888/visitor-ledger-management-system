package com.immutable.visitormanagement.repository;

import com.immutable.visitormanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailIgnoreCase(String email);

    public Optional<User> findByEmail(String username);

    @Query("SELECT u.email FROM User u")
    String[] findAllEmails();
}
