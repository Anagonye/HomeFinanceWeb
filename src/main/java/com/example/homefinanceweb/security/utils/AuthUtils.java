package com.example.homefinanceweb.security.utils;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthUtils {

    private final AppUserService service;

    public AppUser getCurrentLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return (AppUser) service.loadUserByUsername(currentPrincipalName);
    }
}
