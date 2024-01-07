package com.example.transactionservice.service;

import com.example.transactionservice.model.dto.PayoutRequestDto;
import com.example.transactionservice.model.dto.PayoutResponseDto;
import com.example.transactionservice.model.mapper.PaymentMapper;
import com.example.transactionservice.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayoutService {

    private final PayoutRepository payoutRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public Mono<PayoutResponseDto> createPayout(PayoutRequestDto request) {
        return payoutRepository.save(paymentMapper.toPayout(request)).map(paymentMapper::toPayoutRequest);
    }

    @Transactional(readOnly = true)
    public Flux<PayoutResponseDto> getPayoutList(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        }
        return payoutRepository.findByCreatedAtBetween(startDate, endDate).map(paymentMapper::toPayoutRequest);
    }

    @Transactional(readOnly = true)
    public Mono<PayoutResponseDto> getPayoutDetails(Long id) {
        return payoutRepository.findById(id).map(paymentMapper::toPayoutRequest);
    }
}
