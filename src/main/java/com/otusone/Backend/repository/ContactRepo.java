package com.otusone.Backend.repository;

import com.otusone.Backend.model.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<ContactUs,Integer> {
}
