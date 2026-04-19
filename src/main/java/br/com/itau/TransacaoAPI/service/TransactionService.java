package br.com.itau.TransacaoAPI.service;

import br.com.itau.TransacaoAPI.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final List<Transaction> transactionsInMemory = new CopyOnWriteArrayList<>();

    public void addTransaction(Transaction newTransaction) {
        transactionsInMemory.add(newTransaction);
        log.debug("Nova transação em memória guardada: valor:{}, dt={}. Memória total transações: {}",
                newTransaction.valor(), newTransaction.dataHora(), transactionsInMemory.size());
    }

    public void cleanAllMemory() {
        int totalDeleted = transactionsInMemory.size();
        transactionsInMemory.clear();
        log.info("Opa! Todas as transações foram pro saco. Total deletado: {}", totalDeleted);
    }

    public List<Transaction> searchAllRecent(long thoseSeconds) {
        OffsetDateTime limiteDoPassadoCortado = OffsetDateTime.now().minusSeconds(thoseSeconds);
        List<Transaction> transacoesDentroDoMinutoFiltro = transactionsInMemory.stream()
                .filter(t -> t.dataHora().isAfter(limiteDoPassadoCortado))
                .toList();

        log.debug("Acharam: {} transações pros últimos {}s", transacoesDentroDoMinutoFiltro.size(), thoseSeconds);
        return transacoesDentroDoMinutoFiltro;
    }

    // future
    public boolean verificarSeDataENoFuturoTotalmenteInvalida(OffsetDateTime alvo) {
        // "isafter" traduz "Está depois Da(hora atual" = Futuro total :D
        return alvo.isAfter(OffsetDateTime.now());
    }
}
