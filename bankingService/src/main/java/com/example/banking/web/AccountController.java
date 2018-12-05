package com.example.banking.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.banking.model.*;

import com.example.banking.model.AccountRepository;

@RestController
@RequestMapping("/api")
public class AccountController {
	  private final Logger log = LoggerFactory.getLogger(AccountController.class);
	    private AccountRepository accountRepository;
	    private UserRepository userRepository;

	    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
	        this.accountRepository = accountRepository;
	        this.userRepository = userRepository;
	    }

	    @GetMapping("/accounts")
	    Collection<Account> accounts(@RequestParam(required=false) Long userId ) {
	    		if (userId==null || userId==0) {
	    			return accountRepository.findAll();
	    		}
	        return accountRepository.findAccountsByUserId(userId);
	    }

	    @GetMapping("/account/{id}")
	    ResponseEntity<?> getAccount(@PathVariable Long id) {
	        Optional<Account> account = accountRepository.findById(id);
	        return account.map(response -> ResponseEntity.ok().body(response))
	        			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PostMapping("/account")
	    ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) throws URISyntaxException {
	        log.info("Request to create account: {}", account);
	        Account result = accountRepository.save(account);
	        return ResponseEntity.created(new URI("/api/account/" + result.getId()))
	                .body(result);
	    }

	    @PutMapping("/account/{id}")
	    ResponseEntity<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody Account account) {
	        account.setId(id);
	        log.info("Request to update account: {}", account);
	        Account result = accountRepository.save(account);
	        return ResponseEntity.ok().body(result);
	    }
/*
	    @DeleteMapping("/group/{id}")
	    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
	        log.info("Request to delete group: {}", id);
	        groupRepository.deleteById(id);
	        return ResponseEntity.ok().build();
	    }*/
}
