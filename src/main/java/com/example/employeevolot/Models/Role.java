package com.example.employeevolot.Models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, HR;

    @Override
    public String getAuthority() { return name(); }
}
