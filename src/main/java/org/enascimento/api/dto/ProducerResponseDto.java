package org.enascimento.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerResponseDto {
    private String name;
    private Integer previousWin;
    private Integer followingWin;
    private Integer interval;
}