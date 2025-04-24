package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.mapper.MessageMapper;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.model.Message;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

  @Mock
  private MessageRepository repository;

  @Mock
  private MessageMapper mapper;

  @InjectMocks
  private MessageService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getMessagePage_shouldReturnPageOfMessages() {
    // Given
    Message message = new Message();
    MessageDTO dto = MessageDTO.builder()
      .id(1L)
      .sender("sender-app")
      .recipients(List.of("partner-1"))
      .payload("test payload")
      .build();

    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
    when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(message), pageable, 1));
    when(mapper.listMessageToListMessageDto(List.of(message))).thenReturn(List.of(dto));

    // When
    Page<MessageDTO> result = service.getMessagePage(0, 10);

    // Then
    assertEquals(1, result.getTotalElements());
    assertEquals("sender-app", result.getContent().get(0).sender());
  }

  @Test
  void getMessagePage_shouldThrowOnInvalidParams() {
    assertThrows(IllegalArgumentException.class, () -> service.getMessagePage(-1, 10));
    assertThrows(IllegalArgumentException.class, () -> service.getMessagePage(0, 0));
  }

  @Test
  void getMessagePage_shouldThrowIllegalStateExceptionOnError() {
    // Given
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
    when(repository.findAll(pageable)).thenThrow(new RuntimeException("DB unavailable"));

    // When - Then
    IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.getMessagePage(0, 10));
    assertTrue(ex.getMessage().contains("Database error"));
  }

  @Test
  void create_shouldCallRepositorySave() {
    // Given
    CreateMessageDTO dto = CreateMessageDTO.builder()
      .sender("sender")
      .recipients(List.of("partner-x"))
      .payload("message")
      .build();
    Message message = new Message();

    when(mapper.createMessageDtoToMessage(dto)).thenReturn(message);

    // When
    service.create(dto);

    // Then
    verify(repository).save(message);
  }
}
