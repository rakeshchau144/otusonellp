package com.otusone.Backend.repository;

import com.otusone.Backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Admin findFirstByEmail(String signInEmail);
}
