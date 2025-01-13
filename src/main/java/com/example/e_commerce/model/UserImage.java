package com.example.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class UserImage {

    @Id
    private String Id;
    private String url;

    @OneToOne(mappedBy = "userImage", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
