package com.madman.lambda.controller;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.madman.lambda.HelloLambdaApplication;
import com.madman.lambda.StreamLambdaHandler;
import com.madman.lambda.dto.GreetingDto;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HelloLambdaApplication.class })
@WebAppConfiguration
public class HelloControllerTest {

	private MockLambdaContext lambdaContext;

	private SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

	@Autowired
	private ObjectMapper mapper;

	public HelloControllerTest() {
		lambdaContext = new MockLambdaContext();
		this.handler = StreamLambdaHandler.handler;
	}

	@Test
	public void testGreetingApi() throws JsonParseException, JsonMappingException, IOException {
		AwsProxyRequest request = new AwsProxyRequestBuilder("/greeting", "GET").queryString("name", "John").build();
		AwsProxyResponse response = handler.proxy(request, lambdaContext);
	
		assertThat(response.getStatusCode(), equalTo(200));
		GreetingDto responseBody = mapper.readValue(response.getBody(), GreetingDto.class);
		assertThat(responseBody.getMessage(), equalTo("Hello John"));
	}
}