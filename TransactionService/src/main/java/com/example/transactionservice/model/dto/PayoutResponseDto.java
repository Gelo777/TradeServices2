package com.example.transactionservice.model.dto;

import lombok.Data;

@Data
public class PayoutResponseDto {
    private String payoutId;
    private String status;
    private String message;
    private String paymentMethod;
    private Double amount;
    private String currency;
    private Long proselyteTransactionId;
    private String createdAt;
    private String updatedAt;
    private CardDataDto cardData;
    private String language;
    private String notificationUrl;
    private CustomerDto customer;
}
