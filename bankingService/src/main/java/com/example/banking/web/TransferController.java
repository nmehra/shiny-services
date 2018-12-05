package com.example.banking.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.banking.GremlinComponent;
import com.example.banking.model.*;
import com.example.banking.service.Transfer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api")
public class TransferController {
	  private final Logger log = LoggerFactory.getLogger(TransactionController.class);
	    private TransactionRepository transactionRepository;
	    private AccountRepository accountRepository;
	    private GremlinComponent component;
	    @Autowired
	    private Transfer transferService;

	    public TransferController(TransactionRepository transactionRepository, AccountRepository accountRepository, GremlinComponent component) {
	        this.transactionRepository = transactionRepository;
	        this.accountRepository = accountRepository;
	        this.component = component;
	 
	    }

	    @GetMapping("/transfer")
	    Collection<Transaction> transfers() {
	        return transactionRepository.findAll();
	    }

	    @GetMapping("/transfer/{id}")
	    ResponseEntity<?> getTransfer(@PathVariable Long id) {
	        Optional<Transaction> transaction = transactionRepository.findById(id);
	        return transaction.map(response -> ResponseEntity.ok().body(response))
	        			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PostMapping("/transfer")
	    ResponseEntity<?> createTransfer(@Valid @RequestBody Transaction transaction) throws URISyntaxException, IOException {
	    		log.info("Request to create transaction: {}", transaction);
	    		if (transaction.isUseHystrix()) {
	    			return transferService.transfer(transaction, component);
	    		}
	    		
    	 		log.info("Request to create transaction from hystrix wrapped service1: {}", transaction);
    	        transaction.setSuccess(true);
    	        //get account information 
    	        Long accountId = transaction.getFromAccountId();
    	        Optional<Account> accountOpt = accountRepository.findById(accountId);
    	        int balance=0;
    	        Account account = accountOpt.orElse(null);
    	        if (account!=null) 
    	        		balance = account.getBalance();
    	        else 
    	        		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	        if (balance<transaction.getAmount())
    	        {
    	        		transaction.setSuccess(false);
    	        		Transaction result = transactionRepository.save(transaction);
    		        return ResponseEntity.created(new URI("/api/transaction/" + result.getId()))
    		                .body(result);
    	        }
    	        //contact service to enqueue request. remove amount from the remaining balance on success. else save transaction with failure.
    	        
    	        String host = System.getenv("serviceAddress");
    	        final String uri = "http://" + host + ":8082/api/transfer";

    	        //transaction.setDelay(5000);
    	        RestTemplate restTemplate = new RestTemplate();
    	        ResponseEntity<String> result1 = restTemplate.postForEntity(uri, transaction, String.class);

    	        System.out.println(result1);
    	        
    	        account.setBalance(balance-transaction.getAmount());
    	        accountRepository.save(account);
    	        Transaction result = transactionRepository.save(transaction);
    	        return ResponseEntity.created(new URI("/api/transaction/" + result.getId()))
    	                .body(result);
	    }

}
