package com.otusone.Backend.repository;

import com.otusone.Backend.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepo extends JpaRepository<JobPost,Integer> {
}
