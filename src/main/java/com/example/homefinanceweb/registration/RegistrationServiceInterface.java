package com.example.homefinanceweb.registration;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationServiceInterface {

    void register(RegistrationRequest request);
    boolean confirmToken(String token);
}
