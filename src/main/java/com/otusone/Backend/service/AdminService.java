package com.otusone.Backend.service;

import com.otusone.Backend.model.Admin;
import com.otusone.Backend.model.AuthenticationToken;
import com.otusone.Backend.repository.AdminRepo;
import com.otusone.Backend.service.dto.LoginOutput;
import com.otusone.Backend.service.dto.SignInInput;
import com.otusone.Backend.service.emailUtility.EmailHandler;
import com.otusone.Backend.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
    private List<String> otpx=new ArrayList<>();
    public ResponseEntity<String> addAdmin(Admin admin) {
        List<Admin> admins = adminRepo.findAll();
        if(admins.size()>0)return new ResponseEntity<>("Only one Admin are allowed, here one admin already register!! ", HttpStatus.UNAUTHORIZED);
        try{
            String password = PasswordEncrypter.encryptPassword(admin.getPassword());
            admin.setPassword(password);
            Integer x =generateRandomNumber();
            otpx.add(x.toString());
            admin.setOtp(x.toString());
            otpSend(admin.getEmail(),x.toString());
            admin.setOtpStatus(false);
            adminRepo.save(admin);
            return new ResponseEntity<>("admin add",HttpStatus.OK);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static int generateRandomNumber() {
        return (int) ((Math.random() * 900000) + 100000);
    }
    public ResponseEntity<String> otpSend(String email,String otp1) {
        try{
            EmailHandler.sendEmail(email,"Email testing",otp1);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("OTP send on your email verify OTP",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<String> otpVerify(String otp) {
        Admin user = adminRepo.findFirstByOtp(otp);
        if(user ==null) return new ResponseEntity<>("Invalid otp",HttpStatus.NOT_FOUND);
        if(user.getOtp()==null)return new ResponseEntity<>("OTP already use",HttpStatus.NOT_FOUND);
        user.setOtp(null);
        user.setOtpStatus(true);
        adminRepo.save(user);
        return new ResponseEntity<>("OTP verified successfully go for login!!",HttpStatus.OK);
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
            if(admin.getPassword().equals(encryptedPassword) && admin.getOtpStatus()){
                AuthenticationToken authToken = new AuthenticationToken();
                authToken.setTokenValue(UUID.randomUUID().toString());
                addToken(authToken,admin);
                return new ResponseEntity<>(new LoginOutput(authToken,"Login Successfully"),HttpStatus.CREATED);
            }else if(!admin.getOtpStatus() && admin.getPassword().equals(encryptedPassword)){
                Integer otp = generateRandomNumber();
                otpSend(signInEmail,otp.toString());
                admin.setOtp(otp.toString());
                otpx.add(otp.toString());
                adminRepo.save(admin);
                return new ResponseEntity<>(new LoginOutput(null,"Email not verify before login. otp verify then login."),HttpStatus.OK);
            }
            return new ResponseEntity<>(new LoginOutput(null,"Invalid credential ."),HttpStatus.BAD_REQUEST);
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
