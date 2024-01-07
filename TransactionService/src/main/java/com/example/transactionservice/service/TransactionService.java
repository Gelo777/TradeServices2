package com.example.transactionservice.service;

import com.example.transactionservice.model.mapper.PaymentMapper;
import com.example.transactionservice.model.dto.TransactionRequestDto;
import com.example.transactionservice.model.dto.TransactionResponseDto;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto request) {
        return transactionRepository.save(paymentMapper.toTransaction(request))
            .map(paymentMapper::toTransactionDto);
    }

    @Transactional(readOnly = true)
    public Flux<TransactionResponseDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        }
        return transactionRepository.findByCreatedAtBetween(startDate, endDate)
            .map(paymentMapper::toTransactionDto);
    }

    @Transactional(readOnly = true)
    public Mono<TransactionResponseDto> getTransaction(Long id) {
        return transactionRepository.findById(id).map(paymentMapper::toTransactionDto);
    }

}
