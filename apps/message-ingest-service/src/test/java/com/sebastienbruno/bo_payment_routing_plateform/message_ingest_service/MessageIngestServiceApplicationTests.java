package com.sebastienbruno.bo_payment_routing_plateform.message_ingest_service;

import com.sebastienbruno.bo_payment_routing_plateform.message_ingest_service.model.Message;
import com.sebastienbruno.bo_payment_routing_plateform.message_ingest_service.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class MessageIngestServiceApplicationTests {

  @Autowired
  private MessageRepository messageRepository;

  @Test
  void contextLoads() {
  }

  @Test
  void personTableShouldContainData() {
    List<Message> messages = messageRepository.findAll();
    assertThat(messages).isNotEmpty();
  }
}
