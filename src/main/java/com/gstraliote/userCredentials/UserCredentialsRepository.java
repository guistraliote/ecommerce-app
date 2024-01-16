package com.gstraliote.userCredentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<Object> findByUserEmail(String userEmail);
}