package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.listener;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MessageQueueListenerTest {

  private MessageService messageService;
  private MessageQueueListener listener;

  @BeforeEach
  void setUp() {
    messageService = mock(MessageService.class);
    listener = new MessageQueueListener(messageService);
  }

  @Test
  void receiveMessage_shouldCallServiceCreate() {
    // Given
    CreateMessageDTO dto = CreateMessageDTO.builder()
      .sender("test-app")
      .recipients(List.of("recipient"))
      .payload("test-payload")
      .build();

    // When
    listener.receiveMessage(dto);

    // Then
    verify(messageService, times(1)).create(dto);
  }

  @Test
  void receiveMessage_shouldHandleException() {
    // Given
    CreateMessageDTO dto = CreateMessageDTO.builder()
      .sender("app")
      .recipients(List.of("recipient"))
      .payload("payload")
      .build();

    doThrow(new RuntimeException("DB error")).when(messageService).create(dto);

    // When: we don’t expect an exception to be thrown, it should be caught inside the method
    listener.receiveMessage(dto);

    // Then: still verify that the method was called
    verify(messageService).create(dto);
  }
}
