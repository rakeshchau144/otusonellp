package com.otusone.Backend.service;

import com.otusone.Backend.model.AuthenticationToken;
import com.otusone.Backend.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private AuthRepo authRepo;
    public void addToken(List<AuthenticationToken> list) {
        authRepo.saveAll(list);
    }

    public boolean authenticate(String token) {
        AuthenticationToken authToken = authRepo.findFirstByTokenValue(token);
        if (authToken == null) {
            return false;
        }
        return true;
    }
}
