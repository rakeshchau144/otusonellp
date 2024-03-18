package com.otusone.Backend.service;

import com.otusone.Backend.model.JobPost;
import com.otusone.Backend.repository.JobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobPostRepo jobPostRepo;

    public ResponseEntity<String> addJobPost(JobPost jobPost) {
        try{
            jobPostRepo.save(jobPost);
            return new ResponseEntity<>("Post added successfully .", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    public List<JobPost> getJobPost() {
        return jobPostRepo.findAll();
    }

    public ResponseEntity<String> deletePost(Integer id) {
        JobPost jobPost = jobPostRepo.findById(id).orElse(null);
        try {
            if(jobPost==null)return new ResponseEntity<>("Id not matched",HttpStatus.NOT_FOUND);
            else jobPostRepo.delete(jobPost);
            return new ResponseEntity<>("Job post deleted .",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.OK);
        }
    }

    public ResponseEntity<String> updateJobPost(Integer id, String name) {
        JobPost jobPost = jobPostRepo.findById(id).orElse(null);
        try {
            if(jobPost==null)return new ResponseEntity<>("job post not found",HttpStatus.NOT_FOUND);
            else jobPost.setJobProfile(name);
            jobPostRepo.save(jobPost);
            return new ResponseEntity<>("Update completed", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
