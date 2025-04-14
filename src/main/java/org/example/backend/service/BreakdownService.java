package org.example.backend.service;

import lombok.Data;
import org.example.backend.repository.BreakdownRepository;
import org.springframework.stereotype.Service;

@Service
@Data
public class BreakdownService {
    private BreakdownRepository breakdownRepository;
}
