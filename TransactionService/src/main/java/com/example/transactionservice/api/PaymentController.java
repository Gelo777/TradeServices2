package com.example.transactionservice.api;

import com.example.transactionservice.model.dto.PayoutRequestDto;
import com.example.transactionservice.model.dto.PayoutResponseDto;
import com.example.transactionservice.model.dto.TransactionRequestDto;
import com.example.transactionservice.model.dto.TransactionResponseDto;
import com.example.transactionservice.model.mapper.PaymentMapper;
import com.example.transactionservice.service.PayoutService;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final TransactionService transactionService;
    private final PayoutService payoutService;

    @PostMapping("/transaction")
    public Mono<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto requestDto) {
        return transactionService.createTransaction(requestDto);
    }

    @GetMapping("/transaction/list")
    public Flux<TransactionResponseDto> getTransactionList(@RequestParam(required = false, name = "start_date") LocalDateTime startDate,
                                                          @RequestParam(required = false, name = "end_date") LocalDateTime endDate) {
        return transactionService.getTransactionsByDateRange(startDate, endDate);
    }

    @GetMapping("/transaction/{transactionId}/details")
    public Mono<TransactionResponseDto> getTransactionDetails(@PathVariable Long transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @PostMapping("/payout")
    public Mono<PayoutResponseDto> createPayout(@RequestBody PayoutRequestDto requestDto) {
        return payoutService.createPayout(requestDto);
    }

    @GetMapping("/payout/list")
    public Flux<PayoutResponseDto> getPayoutList(@RequestParam(required = false, name = "start_date") LocalDateTime startDate,
                                                 @RequestParam(required = false, name = "end_date") LocalDateTime endDate) {
        return payoutService.getPayoutList(startDate, endDate);
    }

    @GetMapping("/payout/{payoutId}/details")
    public Mono<PayoutResponseDto> getPayoutDetails(@PathVariable Long payoutId) {
        return payoutService.getPayoutDetails(payoutId);
    }
}
