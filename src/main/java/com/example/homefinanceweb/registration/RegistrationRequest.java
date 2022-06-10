package com.example.homefinanceweb.registration;


import lombok.*;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationRequest {
    @NotEmpty(message = "Name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;



}
