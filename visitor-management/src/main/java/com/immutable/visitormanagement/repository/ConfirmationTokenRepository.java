package com.immutable.visitormanagement.repository;

import com.immutable.visitormanagement.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
