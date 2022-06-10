package com.example.homefinanceweb.appuser;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
