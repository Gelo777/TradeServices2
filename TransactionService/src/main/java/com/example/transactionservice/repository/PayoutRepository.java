package com.example.transactionservice.repository;

import com.example.transactionservice.model.entity.Payout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface PayoutRepository extends ReactiveCrudRepository<Payout, Long> {

    Flux<Payout> findByStatus(String status);
    Flux<Payout> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
