package org.example.backend.model.response;

import lombok.Data;
import org.example.backend.model.enums.Status;

@Data
public class StatusResponse {
    private Status status;

    public StatusResponse(Status status) {
        this.status = status;
    }
}
