package com.immutable.visitormanagement.entity;

import jakarta.persistence.Entity;

@Entity
public class UserDetails {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String typeOfAdmin;
}
