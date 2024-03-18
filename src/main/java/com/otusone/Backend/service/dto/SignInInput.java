package com.otusone.Backend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInInput {
    private String email;
    private String password;
   // private String deviceToken;
}
