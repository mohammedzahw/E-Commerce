package com.example.e_commerce.model;

import com.example.e_commerce.dto.ImageDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class UserImage {

    @Id
    private String id;
    private String url;

    @OneToOne(mappedBy = "userImage", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    public UserImage(ImageDto imageDto) {
        this.id = imageDto.getId();
        this.url = imageDto.getUrl();

    }
}
