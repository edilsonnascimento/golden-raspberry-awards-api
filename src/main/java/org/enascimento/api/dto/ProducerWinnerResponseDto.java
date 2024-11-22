package org.enascimento.api.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerWinnerResponseDto {
    private List<ProducerResponseDto> min;
    private List<ProducerResponseDto> max;
}