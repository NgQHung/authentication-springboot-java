package com.example.authentication.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        String fullName,
        @NotBlank(message = "Email cannot be blank")
        @Email(message="Invalid email")
        String email,
        @Size(min=5, max=20, message="Password must be between 5 and 20 characters")
        String password

) {
}
