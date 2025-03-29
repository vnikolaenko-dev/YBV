package org.example.backend.model.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String status;
    private String token;

    public JwtResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }
}
