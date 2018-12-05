package com.example.banking;

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

import com.example.banking.model.Transfer;


@RestController
@RequestMapping("/api")
public class TransferController {
	  private final Logger log = LoggerFactory.getLogger(TransferController.class);
	   
	    public TransferController() {
	       System.out.println("initializing transfer controller");
	    }
	    @GetMapping("/transfer")
	    ResponseEntity<?> transfer(){
	    		System.out.println("in get transfer method");
	    		return new ResponseEntity<>(HttpStatus.OK);
	    }
	    @PostMapping("/transfer")
	    ResponseEntity<?> transfer( @RequestBody Transfer transferObj ) throws InterruptedException {
	    		int delaySec = transferObj.delay;
	    		if (delaySec>0)
	    			Thread.sleep(transferObj.delay*1000);
	    		log.info("Request to create transaction: {}", transferObj);
	    		if (transferObj.success)
	    			return new ResponseEntity<>(HttpStatus.OK);
	    		else
	    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    		
	    }

}
