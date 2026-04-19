package br.com.itau.TransacaoAPI.service;

import br.com.itau.TransacaoAPI.dto.StatisticResponse;
import br.com.itau.TransacaoAPI.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class StatisticService {

    private static final Logger log = LoggerFactory.getLogger(StatisticService.class);
    private static final int CASAS_MEDIA_DIVISAO = 12;

    private final TransactionService asMemoriaTransacoes;

    private final long propJanelaSegundaDaMatematicaConfig;

    public StatisticService(
            TransactionService dServiceAche,
            @Value("${estatistica.janela-segundos:60") long propertiesApplication) {

        this.asMemoriaTransacoes = dServiceAche;
        this.propJanelaSegundaDaMatematicaConfig = propertiesApplication;
    }

    public StatisticResponse facaCalculacao() {
        long temporizadorInicio = System.currentTimeMillis();
        List<Transaction> listaDasRecentesMemoria = asMemoriaTransacoes.searchAllRecent(propJanelaSegundaDaMatematicaConfig);

        if (listaDasRecentesMemoria.isEmpty()) {
            log.debug("Listou tudo: tem nada chefe");
            return StatisticResponse.vazia();
        }

        long aQuantidadeDeTransacDosSegsCount = listaDasRecentesMemoria.size();

        BigDecimal aSomaSumerSometriaTotalS = listaDasRecentesMemoria.stream()
                .map(Transaction::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal minimu = listaDasRecentesMemoria.stream()
                .map(Transaction::valor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal maximu = listaDasRecentesMemoria.stream()
                .map(Transaction::valor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);


        BigDecimal averagem = aSomaSumerSometriaTotalS.divide(BigDecimal.valueOf(aQuantidadeDeTransacDosSegsCount), CASAS_MEDIA_DIVISAO, RoundingMode.HALF_UP);

                long tempoDoProcessAposMiliSecDoPC = System.currentTimeMillis() - temporizadorInicio;
        log.info("Processamento durou pra calculo foi rapido ne: {}ms", tempoDoProcessAposMiliSecDoPC);

        return new StatisticResponse(aQuantidadeDeTransacDosSegsCount, aSomaSumerSometriaTotalS, averagem, minimu, maximu);
    }
}
