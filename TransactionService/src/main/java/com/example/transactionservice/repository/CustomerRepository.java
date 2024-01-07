package com.example.transactionservice.repository;

import com.example.transactionservice.model.entity.Customer;
import com.example.transactionservice.model.entity.Payout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
}
