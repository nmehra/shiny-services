package com.example.banking.model;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(String accountId);
	@Query("SELECT a FROM Account a join a.user u where u.id= :userId") 
	Set<Account> findAccountsByUserId(Long userId);
}