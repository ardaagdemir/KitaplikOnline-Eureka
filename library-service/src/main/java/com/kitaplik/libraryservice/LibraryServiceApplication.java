package com.kitaplik.libraryservice;

import com.kitaplik.libraryservice.client.RetreiveMessageErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class LibraryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	/*
	Circuit Breaker için yoruma alınmıştır.
	Bean oluşturulmadığı için hatalar yakalanmayacaktır.

	//FeignClient Error Decoder
	@Bean
	public ErrorDecoder errorDecoder(){
		return new RetreiveMessageErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}*/
}
