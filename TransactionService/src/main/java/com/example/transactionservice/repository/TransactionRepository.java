package com.example.transactionservice.repository;

import com.example.transactionservice.model.entity.Payout;
import com.example.transactionservice.model.entity.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {

    Flux<Transaction> findByStatus(String status);
    Flux<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
