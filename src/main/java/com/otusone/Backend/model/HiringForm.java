package com.otusone.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class HiringForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hiringFormId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String highestQualification;
    private String experience;
    private String currentCTC;
    private String resume;

}
