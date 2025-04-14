package org.example.backend.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.example.backend.repository.BreakdownRepository;

@Service
@Data
public class BreakdownService {
    private final BreakdownRepository breakdownRepository;
}
