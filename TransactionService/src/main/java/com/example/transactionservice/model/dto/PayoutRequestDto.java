package com.example.transactionservice.model.dto;

import lombok.Data;

@Data
public class PayoutRequestDto {
    private String payoutId;
    private String status;
    private String message;
}
