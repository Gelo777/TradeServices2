package com.example.transactionservice.model.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {
    private Long transactionId;
    private String status;
    private String message;
}
