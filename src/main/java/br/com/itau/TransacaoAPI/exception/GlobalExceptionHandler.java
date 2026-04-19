package br.com.itau.TransacaoAPI.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> trarFaltaDeCamposNoEnvioJsonDoDTOVaiTomaEsseMethodArgNot(MethodArgumentNotValidException errodavida) {

        errodavida.getBindingResult().getFieldErrors().forEach(eDaLista ->
                log.warn("Alerta: User burro enviou Req com campo faltando!: campo {}, oq foi='{}'", eDaLista.getField(), eDaLista.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> erroSeConverterOJsonSairEstranhoLixoNaRedeHttpMessageError(HttpMessageNotReadableException trutu) {
        log.warn("Formatação lixosa enviada na requicião. n entendi nd: {}", trutu.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // acho q dá certo :D
}
