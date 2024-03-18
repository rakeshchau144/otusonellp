package com.otusone.Backend.service.dto;

import com.otusone.Backend.model.AuthenticationToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginOutput {
    private AuthenticationToken token;
    private String massage;
    //private String role;

}
