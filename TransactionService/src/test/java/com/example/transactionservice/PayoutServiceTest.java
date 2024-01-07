package com.example.transactionservice;

import com.example.transactionservice.model.dto.PayoutRequestDto;
import com.example.transactionservice.model.dto.PayoutResponseDto;
import com.example.transactionservice.model.entity.Payout;
import com.example.transactionservice.model.mapper.PaymentMapper;
import com.example.transactionservice.repository.PayoutRepository;
import com.example.transactionservice.service.PayoutService;
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
class PayoutServiceTest {

    @InjectMocks
    private PayoutService payoutService;

    @Mock
    private PayoutRepository payoutRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Test
    void testCreatePayout() {
        PayoutRequestDto request = new PayoutRequestDto();
        Payout payout = new Payout();
        PayoutResponseDto expectedDto = new PayoutResponseDto();

        when(paymentMapper.toPayout(request)).thenReturn(payout);
        when(payoutRepository.save(payout)).thenReturn(Mono.just(payout));
        when(paymentMapper.toPayoutRequest(payout)).thenReturn(expectedDto);

        Mono<PayoutResponseDto> result = payoutService.createPayout(request);

        StepVerifier.create(result)
            .expectNext(expectedDto)
            .verifyComplete();

        verify(paymentMapper, times(1)).toPayout(request);
        verify(payoutRepository, times(1)).save(payout);
        verify(paymentMapper, times(1)).toPayoutRequest(payout);
    }

    @Test
    void testGetPayoutList() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 23, 59, 59);
        Payout payout = new Payout();
        PayoutResponseDto expectedDto = new PayoutResponseDto();

        when(payoutRepository.findByCreatedAtBetween(startDate, endDate)).thenReturn(Flux.just(payout));
        when(paymentMapper.toPayoutRequest(payout)).thenReturn(expectedDto);

        Flux<PayoutResponseDto> result = payoutService.getPayoutList(startDate, endDate);

        StepVerifier.create(result)
            .expectNext(expectedDto)
            .verifyComplete();

        verify(payoutRepository, times(1)).findByCreatedAtBetween(startDate, endDate);
        verify(paymentMapper, times(1)).toPayoutRequest(payout);
    }

    @Test
    void testGetPayoutDetails() {
        Long payoutId = 1L;
        Payout payout = new Payout();
        PayoutResponseDto expectedDto = new PayoutResponseDto();

        when(payoutRepository.findById(payoutId)).thenReturn(Mono.just(payout));
        when(paymentMapper.toPayoutRequest(payout)).thenReturn(expectedDto);

        Mono<PayoutResponseDto> result = payoutService.getPayoutDetails(payoutId);

        StepVerifier.create(result)
            .expectNext(expectedDto)
            .verifyComplete();

        verify(payoutRepository, times(1)).findById(payoutId);
        verify(paymentMapper, times(1)).toPayoutRequest(payout);
    }
}
