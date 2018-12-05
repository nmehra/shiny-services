package com.example.banking;

import java.util.Optional;

import com.gremlin.ApplicationCoordinates;
import com.gremlin.GremlinCoordinatesProvider;
import com.gremlin.aws.AwsApplicationCoordinatesResolver;

public class MyCoordinatesProvider extends GremlinCoordinatesProvider {

	 @Override
	    public ApplicationCoordinates initializeApplicationCoordinates() {
	        return AwsApplicationCoordinatesResolver.inferFromEnvironment().map(c -> {
	            c.putField("userfacing", "true");
	            return c;
	        }).orElseGet(() -> new ApplicationCoordinates.Builder()
	                .withType("BankingService")
	                .withField("name", "banking")
	                .withField("userfacing", "true")
	                .build());
	    
	    }
}