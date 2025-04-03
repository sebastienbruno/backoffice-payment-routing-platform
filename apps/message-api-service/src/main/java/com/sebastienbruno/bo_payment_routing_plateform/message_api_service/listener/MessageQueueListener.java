package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.listener;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.config.JmsConfig;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageQueueListener {

  private final MessageService service;

  public MessageQueueListener(MessageService service) {
    this.service = service;
  }

  @JmsListener(destination = JmsConfig.MESSAGE_QUEUE)
  public void receiveMessage(CreateMessageDTO message) {
    try {
      log.info("Received message from MQ: {}", message);
      this.service.create(message);
    } catch (Exception e) {
      log.error("Failed to process message: {}", message, e);
    }
  }
}
