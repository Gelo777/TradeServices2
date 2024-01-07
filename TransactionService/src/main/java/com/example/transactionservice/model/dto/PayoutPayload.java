package com.example.transactionservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayoutPayload {

    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private Long proselyteTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String type;
    private CardDataDto cardData;
    private String language;
    private CustomerDto customer;
    private String status;
    private String message;
}