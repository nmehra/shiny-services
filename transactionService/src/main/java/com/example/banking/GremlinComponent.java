package com.example.banking;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import com.gremlin.GremlinService;
import com.gremlin.GremlinServiceFactory;
import com.gremlin.http.client.GremlinApacheHttpRequestInterceptor;

@Component
public class GremlinComponent {
	final GremlinServiceFactory gremlinServiceFactory = new GremlinServiceFactory(new MyCoordinatesProvider());
	final GremlinService gremlinService = gremlinServiceFactory.getGremlinService();
	final HttpRequestInterceptor gremlinHttpInterceptor = 
		    new GremlinApacheHttpRequestInterceptor(gremlinService, "sample");
	public final HttpClient outgoingHttpClient = HttpClientBuilder.create()
		    .setDefaultRequestConfig(RequestConfig
		        .custom()
		        .setConnectTimeout(500)
		        .setSocketTimeout(1000)
		        .build()
		    )
		    .addInterceptorLast(gremlinHttpInterceptor)
		    .build();
}
