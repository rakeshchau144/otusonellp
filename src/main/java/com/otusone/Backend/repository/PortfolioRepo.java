package com.otusone.Backend.repository;

import com.otusone.Backend.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Integer> {
}
