package com.example.transactionservice;

import com.example.transactionservice.model.dto.TransactionRequestDto;
import com.example.transactionservice.model.dto.TransactionResponseDto;
import com.example.transactionservice.model.entity.Transaction;
import com.example.transactionservice.model.mapper.PaymentMapper;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testCreateTransaction() {

        TransactionRequestDto request = new TransactionRequestDto();
        Transaction transaction = new Transaction();
        TransactionResponseDto expectedDto = new TransactionResponseDto();

        when(paymentMapper.toTransaction(request)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        when(paymentMapper.toTransactionDto(transaction)).thenReturn(expectedDto);

        Mono<TransactionResponseDto> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
            .expectNext(expectedDto)
            .verifyComplete();

        verify(paymentMapper, times(1)).toTransaction(request);
        verify(transactionRepository, times(1)).save(transaction);
        verify(paymentMapper, times(1)).toTransactionDto(transaction);
    }

    @Test
    void testGetTransactionsByDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 23, 59, 59);
        Transaction transaction = new Transaction();
        TransactionResponseDto expectedDto = new TransactionResponseDto();

        when(transactionRepository.findByCreatedAtBetween(startDate, endDate)).thenReturn(Flux.just(transaction));
        when(paymentMapper.toTransactionDto(transaction)).thenReturn(expectedDto);

        Flux<TransactionResponseDto> result = transactionService.getTransactionsByDateRange(startDate, endDate);

        StepVerifier.create(result)
            .expectNext(expectedDto)
            .verifyComplete();

        verify(transactionRepository, times(1)).findByCreatedAtBetween(startDate, endDate);
        verify(paymentMapper, times(1)).toTransactionDto(transaction);
    }

    @Test
    void testGetTransaction() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        TransactionResponseDto expectedDto = new TransactionResponseDto();

        when(transactionRepository.findById(transactionId)).thenReturn(Mono.just(transaction));
        when(paymentMapper.toTransactionDto(transaction)).thenReturn(expectedDto);

        Mono<TransactionResponseDto> result = transactionService.getTransaction(transactionId);

        StepVerifier.create(result)
            .expectNext(expectedDto)
            .verifyComplete();

        verify(transactionRepository, times(1)).findById(transactionId);
        verify(paymentMapper, times(1)).toTransactionDto(transaction);
    }
}