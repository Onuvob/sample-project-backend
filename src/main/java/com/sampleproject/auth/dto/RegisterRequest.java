package com.sampleproject.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Firstname must not be blank")
    private String firstName;
    @NotBlank(message = "Lastname must not be blank")
    private String lastName;
    @Email
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6)
    private String password;
    private String phone;
}
