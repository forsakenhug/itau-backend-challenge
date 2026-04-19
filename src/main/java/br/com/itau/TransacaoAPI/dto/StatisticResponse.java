package br.com.itau.TransacaoAPI.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record StatisticResponse(
        @Schema(description = "Quantidade de transações", example = "10")
        long count,

        @Schema(description = "Soma total dos valores", example = "1234.56")
        BigDecimal sum,

        @Schema(description = "Média dos valores", example = "123.456")
        BigDecimal avg,

        @Schema(description = "Menor valor", example = "12.34")
        BigDecimal min,

        @Schema(description = "Maior valor", example = "123.56")
        BigDecimal max
) {

    public static StatisticResponse vazia() {
        return new StatisticResponse(
                0L,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }
}
