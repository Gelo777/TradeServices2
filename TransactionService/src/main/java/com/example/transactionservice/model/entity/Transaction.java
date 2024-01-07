package com.example.transactionservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("transaction")
public class Transaction {
    @Id
    private Long id;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private Long proselyteTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CardData cardData;
    private String language;
    private String notificationUrl;
    private Customer customer;
    private String status;
    private String message;
}
