package com.example.banking;

import java.util.Optional;

import com.gremlin.ApplicationCoordinates;
import com.gremlin.GremlinCoordinatesProvider;

public class MyCoordinatesProvider extends GremlinCoordinatesProvider {

	 @Override
	    public ApplicationCoordinates initializeApplicationCoordinates() {
	        return new ApplicationCoordinates.Builder()
	                .withType("BankingService")
	                .withField("name", "banking")
	                .withField("userfacing", "true")
	                .build();
	    }
}