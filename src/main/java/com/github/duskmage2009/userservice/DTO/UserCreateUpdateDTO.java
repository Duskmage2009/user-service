package com.github.duskmage2009.userservice.DTO;

import com.github.duskmage2009.userservice.exeption.CheckAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateUpdateDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid",regexp="^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String email;
    @NotBlank(message = "Name is required")
    private String firstName;
    @NotBlank (message = "Last name is required")
    private String lastName;
    @NotNull(message = "Birthdate is required ")
    @CheckAge
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
