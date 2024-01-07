package com.example.transactionservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("card_data")
public class CardData {
    @Id
    private Long id;
    private String cardNumber;
    private String expDate;
    private String cvv;
}