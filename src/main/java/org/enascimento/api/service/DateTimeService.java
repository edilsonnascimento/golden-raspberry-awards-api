package org.enascimento.api.service;

import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class DateTimeService {

    public LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now();
    }
}