package com.otusone.Backend.service;

import com.otusone.Backend.model.ContactUs;
import com.otusone.Backend.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;

    public ResponseEntity<String> contactUs(ContactUs contact) {
        contactRepo.save(contact);
        return new ResponseEntity<>("Detail added .", HttpStatus.OK);
    }
}
