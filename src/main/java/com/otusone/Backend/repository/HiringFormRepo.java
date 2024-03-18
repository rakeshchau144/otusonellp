package com.otusone.Backend.repository;

import com.otusone.Backend.model.HiringForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiringFormRepo extends JpaRepository<HiringForm,Integer> {
    HiringForm findFirstByEmail(String email);
}
