package com.vitali.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
//
//    USER("user"),
//    ADMIN("admin");
//
//    private final String authority;
//
//    Role(String authority) {
//        this.authority = authority;
//    }
//
//    @Override
//    public String getAuthority() {
//        return authority.toUpperCase();
//    }

}