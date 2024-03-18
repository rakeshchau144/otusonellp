package com.otusone.Backend.service;

import com.otusone.Backend.model.Chat;
import com.otusone.Backend.service.emailUtility.EmailHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class ChatService {


    public ResponseEntity<String> letsTalk(Chat chat) throws MessagingException {
        String message = "Mr. "+chat.getFullName()+" " +
                "wants chat with you " +
                "\n" +
                "\n" +
                "His details." +
                "\n" +
                "Name :- "+chat.getFullName()+"" +
                "\nEmail :- "+chat.getEmail()+"" +
                "\nPhone Number :- "+chat.getNumber()+"" +
                "\nTopic he want to talk - "+chat.getMassage()+"";
        EmailHandler.sendEmail("rakeshoutsone@gmail.com","Client want chat with you",message);
        return new ResponseEntity<>("message send to admin !!", HttpStatus.OK);
    }
}
