package com.example.transactionservice.model.mapper;

import com.example.transactionservice.model.dto.CardDataDto;
import com.example.transactionservice.model.dto.CustomerDto;
import com.example.transactionservice.model.dto.PayoutResponseDto;
import com.example.transactionservice.model.dto.PayoutRequestDto;
import com.example.transactionservice.model.dto.TransactionRequestDto;
import com.example.transactionservice.model.dto.TransactionResponseDto;
import com.example.transactionservice.model.entity.CardData;
import com.example.transactionservice.model.entity.Customer;
import com.example.transactionservice.model.entity.Payout;
import com.example.transactionservice.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mappings({
        @Mapping(target = "cardData", source = "cardDataDto"),
        @Mapping(target = "customer", source = "customerDto")
    })
    Transaction toTransaction(TransactionRequestDto transactionDto);

    @Mappings({
        @Mapping(target = "cardDataDto", source = "cardData"),
        @Mapping(target = "customerDto", source = "customer")
    })
    TransactionResponseDto toTransactionDto(Transaction transaction);

    @Mappings({
        @Mapping(target = "cardData", source = "cardDataDto"),
        @Mapping(target = "customer", source = "customerDto")
    })
    Payout toPayout(PayoutRequestDto payoutRequest);

    @Mappings({
        @Mapping(target = "cardDataDto", source = "cardData"),
        @Mapping(target = "customerDto", source = "customer")
    })
    PayoutResponseDto toPayoutDto(Payout payout);

    Flux<TransactionResponseDto> toTransactionDtoList(Flux<Transaction> transactions);

    CardDataDto toCardDataDto(CardData cardData);

    CardData toCardData(CardDataDto cardDataDto);

    CustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CustomerDto customerDto);

    TransactionResponseDto toTransactionRequest(TransactionRequestDto transactionDto);

    PayoutResponseDto toPayoutRequest(Payout payout);
}
