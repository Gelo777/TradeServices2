package com.example.transactionservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("payouts")
public class Payout {
    @Id
    private Long payoutId;
    private String status;
    private String message;
    private String paymentMethod;
    private Double amount;
    private String currency;
    private Long proselyteTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CardData cardData;
    private String language;
    private String notificationUrl;
    private Customer customer;
}
