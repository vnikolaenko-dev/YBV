package org.example.backend.dto.request;


import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String password;
    private String email;
}
