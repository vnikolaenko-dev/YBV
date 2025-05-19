package org.example.backend.dto.request;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HabitRequest {
    private String name;
    private LocalDateTime dateOfStart;
    private int target = 30;
    private boolean isGood;
}
