package com.otusone.Backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    private String tokenValue;
    //mapping
    @ManyToOne
    @JoinColumn(name = "fk_admin_id")
    private Admin admin;

//    public AuthenticationToken(User user) {
//        this.user = user;
//        this.tokenValue= UUID.randomUUID().toString();
//
//
//    }
//    public AuthenticationToken(Vendor vendor ){
//        this.vendor = vendor;
//    }
//    public AuthenticationToken(Contractor contractor) {
//        this.contractor = contractor;
//    }

}

