package com.example.banking.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.banking.GremlinComponent;
import com.example.banking.model.Account;
import com.example.banking.model.AccountRepository;
import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionRepository;
import com.example.banking.web.TransactionController;
import com.google.gson.Gson;
import com.gremlin.http.client.GremlinApacheHttpRequestInterceptor;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class Transfer {
	private final Logger log = LoggerFactory.getLogger(Transfer.class);
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	
    @HystrixCommand(fallbackMethod = "defaultTransfer",
    		commandProperties = {
    				@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")})
    public ResponseEntity<?> transfer(Transaction transaction, GremlinComponent component) throws URISyntaxException, UnsupportedEncodingException, ClientProtocolException, IOException{
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
	        Gson gson = new Gson();
	        HttpPost httpPost = new HttpPost(uri);
	        
	        HttpEntity request = new StringEntity(gson.toJson(transaction));
	        httpPost.addHeader("Content-Type","application/json");
	        httpPost.addHeader("Accept","application/json");
	        httpPost.setEntity(request);
	        HttpResponse result1 = null;
	        try {
	        		 result1 = component.outgoingHttpClient.execute(httpPost);
	        }
	        catch(Exception ex) {
	        		log.error("the response was error: {}", ex.getMessage());
	        }
	        //transaction.setDelay(5000);
	        
	        //RestTemplate restTemplate = new RestTemplate();
	        //ResponseEntity<String> result1 = restTemplate.postForEntity(uri, transaction, String.class);

	        log.info("the response from service {}", result1.toString());
	        
	        account.setBalance(balance-transaction.getAmount());
	        accountRepository.save(account);
	        Transaction result = transactionRepository.save(transaction);
	        return ResponseEntity.created(new URI("/api/transaction/" + result.getId()))
	                .body(result);
    }
  
    private ResponseEntity<?> defaultTransfer(Transaction transaction, GremlinComponent component) {
    		return  ResponseEntity.ok("Transfer service is down. Please try again after sometime.");
    }
}