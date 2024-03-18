package com.otusone.Backend.service;

import com.otusone.Backend.model.Portfolio;
import com.otusone.Backend.repository.PortfolioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepo portfolioRepo;

    public ResponseEntity<String> addPortfolio(Portfolio portfolio) {
        portfolioRepo.save(portfolio);
        return new ResponseEntity<>("Portfolio add successfully .", HttpStatus.OK);
    }

    public List<Portfolio> getPortfolio() {
        return portfolioRepo.findAll();

    }
    public ResponseEntity<String> deletePortfolio(Integer id) {
        Portfolio portfolio = portfolioRepo.findById(id).orElse(null);
        try{
            if(portfolio == null)return new ResponseEntity<>("Not found . ",HttpStatus.NOT_FOUND);
            else portfolioRepo.delete(portfolio);
            return new ResponseEntity<>("Portfolio deleted .",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Internal server error .",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updatePortfolio(Portfolio portfolio, Integer id) {
        Portfolio portfolio1 = portfolioRepo.findById(id).orElse(null);
        if(portfolio1 == null)return new ResponseEntity<>("Portfolio not found .",HttpStatus.NOT_FOUND);
        else {
            portfolio1.setImg(portfolio.getImg());
            portfolio1.setHeading(portfolio.getHeading());
            portfolio1.setDescription(portfolio.getDescription());
            portfolioRepo.save(portfolio1);
        }
        return new ResponseEntity<>("Portfolio update .",HttpStatus.OK);
    }
}
