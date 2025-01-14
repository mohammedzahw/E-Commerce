package com.example.e_commerce.model;

public interface IUser {
    String getEmail();

    Integer getId();

    String getPassword();

    boolean isActive();

    String getName();
}
