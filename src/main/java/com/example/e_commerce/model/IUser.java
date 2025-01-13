package com.example.e_commerce.model;

public interface IUser {
    String getEmail();

    Long getId();

    String getPassword();

    boolean isActive();

    String getName();
}
