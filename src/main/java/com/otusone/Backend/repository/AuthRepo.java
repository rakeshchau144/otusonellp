package com.otusone.Backend.repository;

import com.otusone.Backend.model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findFirstByTokenValue(String token);
}
