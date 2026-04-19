package br.com.itau.TransacaoAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(description = "Payload de uma transação de entrada pela API")
public record TransactionRequest(

        @Schema(description = "Valor da transação (deve ser >= 0)", example = "123.45")
        @NotNull(message = "O campo 'valor é obrigatório")
        BigDecimal valor,

        // String parser correta
        @Schema(description = "Data/hora da transação no formato ISO 8061 com Offset", example = "2020-08-07T12:34:56.789-03:00")
        @NotNull(message = "O campo 'dataHora' é obrigatório")

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OffsetDateTime dataHora
)
{}
