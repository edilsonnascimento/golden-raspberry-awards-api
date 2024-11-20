package org.enascimento.api.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducesWinnerResponseDto {
    private List<ProducerResponseDto> min;
    private List<ProducerResponseDto> max;
}