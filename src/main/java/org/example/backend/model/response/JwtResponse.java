package org.example.backend.model.response;

import lombok.Data;
import org.example.backend.model.enums.Status;

@Data
public class JwtResponse {
    private Status status;
    private String token;
    private String name;

    public JwtResponse(Status status, String token) {
        this.status = status;
        this.token = token;
    }

    public JwtResponse(Status status, String token, String name) {
        this.status = status;
        this.token = token;
        this.name = name;
    }
}
