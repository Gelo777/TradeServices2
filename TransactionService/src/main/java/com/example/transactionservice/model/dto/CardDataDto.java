package com.example.transactionservice.model.dto;

import lombok.Data;

@Data
public class CardDataDto {
    private String cardNumber;
    private String expDate;
    private String cvv;
}
