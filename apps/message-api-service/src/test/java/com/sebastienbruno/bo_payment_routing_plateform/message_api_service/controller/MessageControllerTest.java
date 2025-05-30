package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.controller;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.config.JmsConfig;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class MessageControllerTest {

  private MessageService messageService;
  private JmsTemplate jmsTemplate;
  private MessageController controller;

  @BeforeEach
  void setUp() {
    messageService = mock(MessageService.class);
    jmsTemplate = mock(JmsTemplate.class);
    controller = new MessageController(messageService, jmsTemplate);
  }

  @Test
  void getMessages_shouldReturnPartialContent_whenHasNextPage() {
    // Given
    MessageDTO message = MessageDTO.builder()
      .id(1L)
      .sender("sender")
      .recipients(List.of("receiver"))
      .payload("Hello")
      .build();

    Page<MessageDTO> page = new PageImpl<>(List.of(message), PageRequest.of(0, 1), 10);
    when(messageService.getMessagePage(0, 1)).thenReturn(page);

    // When
    ResponseEntity<Page<MessageDTO>> response = controller.getMessages(0, 1);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PARTIAL_CONTENT);
    assertThat(response.getHeaders()).containsKeys("X-Total-Count", "X-Page-Number", "X-Page-Size");
    assertThat(response.getBody()).isEqualTo(page);
  }

  @Test
  void getMessages_shouldReturnOk_whenNoNextPage() {
    // Given
    Page<MessageDTO> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
    when(messageService.getMessagePage(0, 10)).thenReturn(page);

    // When
    ResponseEntity<Page<MessageDTO>> response = controller.getMessages(0, 10);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(page);
  }

  @Test
  void sendMessage_shouldReturnOk_whenMessageSentSuccessfully() {
    // Given
    CreateMessageDTO dto = CreateMessageDTO.builder()
      .sender("sender")
      .recipients(List.of("receiver"))
      .payload("message content")
      .build();

    // When
    ResponseEntity<String> response = controller.sendMessage(dto);

    // Then
    verify(jmsTemplate).convertAndSend(JmsConfig.MESSAGE_QUEUE, dto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("OK");
  }

}
