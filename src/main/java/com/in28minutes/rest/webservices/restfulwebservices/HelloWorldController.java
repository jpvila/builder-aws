package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.in28minutes.rest.util.DynamoDBBean;

@RestController
@ComponentScan("com.in28minutes.rest")
public class HelloWorldController {
	private static final AmazonS3 s3Client =  AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
	
	@Autowired
	DynamoDBBean dynamoDBBean;
	
	
	@GetMapping(path = "/hello-world-bean")
	public String helloWorld() {
		System.out.println("imprimiendo logs");
		return "Hola mundo";
	}
	
	///hello-world/path-variable/in28minutes
	@GetMapping(path = "/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		
		//QQQ Upload objeto a S3
	
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
    @GetMapping("/")
    public String home() {
		uploadObjetoS3();
        return "Hello from Spring Boot! " + dynamoDBBean.getNombre();
    }
    
    
    public void uploadObjetoS3() {
    	String key = "prueba" + System.currentTimeMillis() + ".txt";
    	String json = "{ \"nombre\": \"pepe\"}"; 
    	String bucket = "builder-aws";
    	s3Client.putObject(bucket, key , json);
    }
}
