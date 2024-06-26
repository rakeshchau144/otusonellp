package com.otusone.Backend.controller;

import com.otusone.Backend.fileUploadSetUp.CloudinaryController;
import com.otusone.Backend.model.*;
import com.otusone.Backend.service.*;
import com.otusone.Backend.service.dto.LoginOutput;
import com.otusone.Backend.service.dto.SignInInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;
@RestController
public class AdminController {
    @Autowired
    private JobService jobService;
    @Autowired
    private AdminService adminService;
    @PostMapping("/register/admin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }
    @PostMapping("/verify/otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String otp){
        return adminService.otpVerify(otp);
    }
    @PostMapping("login/admin")
    public ResponseEntity<LoginOutput> adminLogin(@RequestBody SignInInput signInInput){
        return adminService.adminLogin(signInInput);
    }
    @Autowired
    private AuthService authService;
    @PostMapping("/add/job/post")
    public ResponseEntity<String> addJobPost(@RequestParam String token,@RequestBody JobPost jobPost){
        if(authService.authenticate(token)){
            return jobService.addJobPost(jobPost);
        }
        return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/get/job/post")
    public ResponseEntity<?> getJobPost(){
        List<JobPost> jobPosts = jobService.getJobPost();
        if (!jobPosts.isEmpty()) return new ResponseEntity<>(jobPosts, HttpStatus.OK);
        return new ResponseEntity<>("Empty", HttpStatus.OK);
    }
    @DeleteMapping("/delete/job/post")
    public ResponseEntity<String> deleteJobPost(@RequestParam String token,@RequestParam Integer id){
        if(authService.authenticate(token)) {
            return jobService.deletePost(id);
        }return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/update/job/name")
    private ResponseEntity<String> updateJobPost(@RequestParam String token,@RequestParam Integer id, @RequestParam String name){
        if(authService.authenticate(token)) {
            return jobService.updateJobPost(id, name);
        }
        return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private CloudinaryController cloudinaryController;
    @PostMapping("/add/portfolio")
    public ResponseEntity<String> addPortfolio(@RequestParam("token") String token,
                                               @RequestParam("image") MultipartFile image,
                                               @RequestParam("heading") String heading,
                                               @RequestParam("description") String description) {
        if(authService.authenticate(token)) {
            try {
                String imageUrl = cloudinaryController.upload(image);
                Portfolio portfolio = new Portfolio();
                portfolio.setImg(imageUrl);
                portfolio.setHeading(heading);
                portfolio.setDescription(description);
                return portfolioService.addPortfolio(portfolio);
            } catch (Exception e) {
                return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/get/portfolio")
    public ResponseEntity<?> getPortfolio(){
            List<Portfolio> portfolio= portfolioService.getPortfolio();
            if (!portfolio.isEmpty()) return new ResponseEntity<>(portfolio, HttpStatus.OK);
            else return new ResponseEntity<>("Empty", HttpStatus.OK);

    }
    @PutMapping("/update/portfolio")
    private ResponseEntity<String> updatePortFolio(@RequestParam("token") String token,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("heading") String heading,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("id") Integer id) throws IOException {
        if(authService.authenticate(token)) {
            String imageUrl = cloudinaryController.upload(image);
            Portfolio portfolio = new Portfolio();
            portfolio.setImg(imageUrl);
            portfolio.setHeading(heading);
            portfolio.setDescription(description);
            return portfolioService.updatePortfolio(portfolio, id);
        }
        return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/delete/portfolio")
    public ResponseEntity<String> deletePortfolio(@RequestParam String token,@RequestParam Integer id){
        if(authService.authenticate(token)) {
            return portfolioService.deletePortfolio(id);
        }
        return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @Autowired
    private HiringFormService hiringFormService;
    @PostMapping("/hiring/form")
    public ResponseEntity<String> addHiringForm(@RequestParam("file") MultipartFile file,
                                                @RequestParam("firstName") String firstName,
                                                @RequestParam("lastName") String lastName,
                                                @RequestParam("email") String email,
                                                @RequestParam("phoneNumber") String phoneNumber,
                                                @RequestParam("highestQualification") String highestQualification,
                                                @RequestParam("experience") String experience,
                                                @RequestParam("currentCTC") String currentCTC
                                                ) throws IOException, MessagingException {
        HiringForm hiringForm  = new HiringForm();
        hiringForm.setFirstName(firstName);
        hiringForm.setLastName(lastName);
        hiringForm.setEmail(email);
        hiringForm.setPhoneNumber(phoneNumber);
        hiringForm.setHighestQualification(highestQualification);
        hiringForm.setExperience(experience);
        hiringForm.setCurrentCTC(currentCTC);
        return hiringFormService.addHiringForm(hiringForm,file);
    }

    @GetMapping("/get/hiring/forms")
    public ResponseEntity<?> getHiringForms(@RequestParam String token){
        if(authService.authenticate(token)) {
            List<HiringForm> list = hiringFormService.getHiringForms();
            if (list.size() == 0) return new ResponseEntity<>("Empty", HttpStatus.CREATED);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @Autowired
    private ContactService contactService;
    @PostMapping("/contact/us")
    public ResponseEntity<String> contactUs(@RequestBody ContactUs contact){
        return contactService.contactUs(contact);
    }
    @PostMapping("/get/contact")
    public ResponseEntity<?> getContact(@RequestParam String token){
        if(authService.authenticate(token)){
            return contactService.getContact();
        }return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/delete/contact/info")
    public ResponseEntity<String> deleteContactInfo(@RequestBody String token,@RequestParam Integer contactId){
        if(authService.authenticate(token)){
            return contactService.deleteContactInfo(contactId);
        }
        return new ResponseEntity<>("Admin not found",HttpStatus.UNAUTHORIZED);
    }
    @Autowired
    private ChatService chatService;
    @PostMapping("/lets/talk")
    public ResponseEntity<String> letsTalk(@RequestBody Chat chat) throws MessagingException {
        return chatService.letsTalk(chat);
    }
}
