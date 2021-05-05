package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.amazonaws.services.cloudwatchevents.model.PutEventsResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.in28minutes.rest.util.DynamoDBBean;

@RestController
@ComponentScan("com.in28minutes.rest")
public class HelloWorldController {
	private static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    private static final AmazonCloudWatchEvents cwe = AmazonCloudWatchEventsClientBuilder.defaultClient();

	@Autowired
	DynamoDBBean dynamoDBBean;

	@GetMapping(path = "/hello-world-bean")
	public String helloWorld() {
		System.out.println("imprimiendo logs");
		return "Hola mundo";
	}

	/// hello-world/path-variable/in28minutes
	@GetMapping(path = "/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {

		// QQQ Upload objeto a S3

		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

	@GetMapping("/")
	public String home() {
		putLogEventCloudWatch();

		uploadObjetoS3();

		return "Hello from Spring Boot! " + dynamoDBBean.getNombre();
	}

	private void putLogEventCloudWatch() {

        final String USAGE =
            "To run this example, supply a resource arn\n" +
            "Ex: PutEvents <resource-arn>\n";
        String resource_arn = "arn:aws:logs:us-east-1:124709293187:log-group:BALTRTransferOnlineLogs:*";

   
        final String EVENT_DETAILS =
            "{ \"key1\": \"value1\", \"key2\": \"value2\" }";

        PutEventsRequestEntry request_entry = new PutEventsRequestEntry()
            .withDetail(EVENT_DETAILS)
            .withDetailType("sampleSubmitted")
            .withResources(resource_arn)
            .withSource("aws-sdk-java-cloudwatch-example");

        PutEventsRequest request = new PutEventsRequest()
            .withEntries(request_entry);

        PutEventsResult response = cwe.putEvents(request);

		
	}

	public void uploadObjetoS3() {
		String key = "prueba" + System.currentTimeMillis() + ".txt";
		String json = "{ \"nombre\": \"pepe\"}";
		String bucket = "builder-aws";
		s3Client.putObject(bucket, key, json);
	}
}
