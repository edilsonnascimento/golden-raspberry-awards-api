package org.enascimento.api.controller;

import org.enascimento.api.dto.ProducerWinnerResponseDto;
import org.enascimento.api.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/producers")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/min-max-winners")
    public ProducerWinnerResponseDto findMinAndMaxWinnerRange() {
        return producerService.findMinAndMaxWinnerRange();
    }
}