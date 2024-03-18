package com.otusone.Backend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpOutput {
    private boolean signUpStatus;
    private String signUpMessage;
}
