package br.com.itau.TransacaoAPI.controller;

import br.com.itau.TransacaoAPI.dto.TransactionRequest;
import br.com.itau.TransacaoAPI.model.Transaction;
import br.com.itau.TransacaoAPI.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transacao") // rota q o cara pediu :)
@Tag(name = "Transações", description = "Endpoints p receber as transações") // estético rs
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    // injeção de dependencia
    private final TransactionService nossaAreaDeTrabalhoDoService;

    public TransactionController(TransactionService oServiceForaDoSpring) {
        this.nossaAreaDeTrabalhoDoService = oServiceForaDoSpring;
    }

    @PostMapping
    @Operation(summary = "Adiciona requisição nova financeira", description = "Aqui a grana extra rs")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "201 é create meu truta, transação rolou"),
            @ApiResponse(responseCode = "422", description = "Seja futuro, ou negativa, a regra de negócio disse q n pode!!! = 422"),
            @ApiResponse(responseCode = "400", description = "Json cagado = 400")
    }) // vms ignorar isso por enquanto ok?

    public ResponseEntity<Void> receberViaWebDePost(@Valid @RequestBody TransactionRequest requisisaoQueVemDoBodyJSON) {
        log.debug("A porta WEB foi batida: VLR: {}, DH: {}", requisisaoQueVemDoBodyJSON.valor(), requisisaoQueVemDoBodyJSON.dataHora());


        if (nossaAreaDeTrabalhoDoService.verificarSeDataENoFuturoTotalmenteInvalida(requisisaoQueVemDoBodyJSON.dataHora())) {
            return ResponseEntity.unprocessableEntity().build(); // ignoramos, ate pq um dev bom nao testa antes kk
        }

        if (requisisaoQueVemDoBodyJSON.valor().compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Alerta 422: Tem valor sendo MENOR que zero: ({}), tiração com o banco", requisisaoQueVemDoBodyJSON.valor());
            // ai itau, 422 pra vcs
            return ResponseEntity.unprocessableEntity().build();
        }

        Transaction modeloAEnviarODataMemory = new Transaction(requisisaoQueVemDoBodyJSON.valor(), requisisaoQueVemDoBodyJSON.dataHora());
        nossaAreaDeTrabalhoDoService.addTransaction(modeloAEnviarODataMemory);

        return ResponseEntity.status(201).build();
        // primeiro commit
    }

    @DeleteMapping
    @Operation(summary = "Exterminar as transações do passado")
    public ResponseEntity<Void> detonarTodasAsMemoriasDeTransacoesOkkG() {
        log.info("Entregando request de Delete.");
        nossaAreaDeTrabalhoDoService.cleanAllMemory();

        return ResponseEntity.ok().build();
        // segundo commit
    }
}
