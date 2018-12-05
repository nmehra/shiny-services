package com.example.banking.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	//List<Transaction> findByFromAccount_Id(Long accountId);	
}
