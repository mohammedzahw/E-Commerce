package com.example.e_commerce;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Response {

    private HttpStatus status;
    private Object data;
    private String message;

    public Response(HttpStatus status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
