package br.com.itau.TransacaoAPI.controller;

import br.com.itau.TransacaoAPI.dto.StatisticResponse;
import br.com.itau.TransacaoAPI.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@Tag(name = "Estatisticas")
public class StatisticController {

    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    private final StatisticService estatiCalculoDoSerivcs;

    public StatisticController(StatisticService dEstatisticaL) {
        this.estatiCalculoDoSerivcs = dEstatisticaL;
    }

    @GetMapping
    @Operation(summary = "Puxa estatisticas", description = "As famosas stats")
    @ApiResponse(responseCode = "200", description = "200 ok json do retorno de count sum etc")
    public ResponseEntity<StatisticResponse> pegaCalculationsGET() {
        log.debug("Get de /estatistica acessado c sucesso web");

        StatisticResponse respostaDasAcoes = estatiCalculoDoSerivcs.facaCalculacao();

        return ResponseEntity.ok(respostaDasAcoes);
    }
    // 3 commit :D (esquece que aquele 3 existiu, pq eu errei o 2)
}
