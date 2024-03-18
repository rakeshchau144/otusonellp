package com.otusone.Backend.service;

import com.otusone.Backend.model.HiringForm;
import com.otusone.Backend.repository.HiringFormRepo;
import com.otusone.Backend.service.emailUtility.EmailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class HiringFormService {
    @Autowired
    private HiringFormRepo hiringFormRepo;


    public ResponseEntity<String> addHiringForm(HiringForm hiringForm, MultipartFile file) throws MessagingException, IOException {
        List<HiringForm> hiringFormList = hiringFormRepo.findAll();
        for (HiringForm hiringForm1 : hiringFormList) {
            if (hiringForm.getEmail().equals(hiringForm1.getEmail()))
                return new ResponseEntity<>("You are already applied !!", HttpStatus.BAD_REQUEST);
        }
        hiringFormRepo.save(hiringForm);
        String massage = "Hi " + hiringForm.getFirstName() +
                "\n" +
                " Thanks for your interest in our company" +
                "\n" +
                "\nI'm HR at OTUSONE LLP Noida and I'd like to take your application to next step." +
                "\nIf your resume shortlisted then our team will contact you " +
                "\n All the best" +
                "\n" +
                "\n" +
                "\n" +
                "\n"+
                "\nKind regards"+
                "\nAparna Singh"+
                "\nOTUSONE LLP";
        File f = convertMultipartFileToFile(file);
        EmailHandler.sendPdf("rakeshoutsone@gmail.com","Resume",f);
        EmailHandler.sendEmail(hiringForm.getEmail(),"do_not_reply",massage);
        return new ResponseEntity<>("Your application has been submitted. Hr will contact you soon !!",HttpStatus.OK);
    }
    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile(multipartFile.getOriginalFilename(), ".pdf");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
    public List<HiringForm> getHiringForms() {
        return hiringFormRepo.findAll();
    }
}
