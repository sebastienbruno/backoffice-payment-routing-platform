package com.sebastienbruno.bo_payment_routing_plateform.message_api_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MessageApiServiceApplication {

	public static void main(String[] args) {
    SpringApplication.run(MessageApiServiceApplication.class, args);
  }

}
