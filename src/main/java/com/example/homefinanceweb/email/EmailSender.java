package com.example.homefinanceweb.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailSender {
    void send(String to, String email);
}
