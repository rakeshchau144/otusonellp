package com.otusone.Backend.service;

import com.otusone.Backend.model.Admin;
import com.otusone.Backend.model.AuthenticationToken;
import com.otusone.Backend.repository.AdminRepo;
import com.otusone.Backend.service.dto.LoginOutput;
import com.otusone.Backend.service.dto.SignInInput;
import com.otusone.Backend.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private AuthService authService;

    public ResponseEntity<String> addAdmin(Admin admin) {
        List<Admin> admins = adminRepo.findAll();
        if(admins.size()>0)return new ResponseEntity<>("Only one Admin are allowed, here one admin already register!! ", HttpStatus.UNAUTHORIZED);
        try{
            String password = PasswordEncrypter.encryptPassword(admin.getPassword());
            admin.setPassword(password);
            adminRepo.save(admin);
            return new ResponseEntity<>("admin add",HttpStatus.OK);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<LoginOutput> adminLogin(SignInInput signInInput) {
        String signInEmail=signInInput.getEmail();
        if(signInEmail==null || signInInput.getPassword()==null){
            return new ResponseEntity<>(new LoginOutput(null,"Please enter Email"),HttpStatus.BAD_REQUEST);
        }
        Admin admin = adminRepo.findFirstByEmail(signInEmail);
        if(admin==null){
            return new ResponseEntity<>(new LoginOutput(null,"Admin not registered !!"),HttpStatus.UNAUTHORIZED);
        }
        try {
            String encryptedPassword= PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(admin.getPassword().equals(encryptedPassword)){
                AuthenticationToken authToken = new AuthenticationToken();
                authToken.setTokenValue(UUID.randomUUID().toString());
                //authToken.setUser(existingUser);
                addToken(authToken,admin);
                return new ResponseEntity<>(new LoginOutput(authToken,"Login Successfully"),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(new LoginOutput(null,"Invalid credentials !!"),HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new LoginOutput(null,"Internal error occurred during sign in"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void addToken(AuthenticationToken authenticationToken,Admin admin){
        List<AuthenticationToken> list = new ArrayList<>();
        authenticationToken.setAdmin(admin);
        list.add(authenticationToken);
        authService.addToken(list);
    }
}
