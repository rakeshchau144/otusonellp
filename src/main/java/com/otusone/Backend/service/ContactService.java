package com.otusone.Backend.service;

import com.otusone.Backend.model.ContactUs;
import com.otusone.Backend.repository.ContactRepo;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;

    public ResponseEntity<String> contactUs(ContactUs contact) {
        contactRepo.save(contact);
        return new ResponseEntity<>("Detail added .", HttpStatus.OK);
    }

    public ResponseEntity<?> getContact() {
        List<ContactUs> contactUses = contactRepo.findAll();
        return new ResponseEntity<>(contactUses,HttpStatus.OK);
    }

    public ResponseEntity<String> deleteContactInfo(Integer contactId) {
        ContactUs contactUs = contactRepo.findById(contactId).orElse(null);
        if(contactUs== null)return new ResponseEntity<>("Contact info not found !!",HttpStatus.NOT_FOUND);
        contactRepo.delete(contactUs);
        return new ResponseEntity<>("delete done !!",HttpStatus.OK);
    }
}
