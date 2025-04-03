package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.controller;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService service;

  public MessageController(MessageService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<MessageDTO>> getMessages(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    Page<MessageDTO> messageDtoPage = service.getMessagePage(page, size);
    if (messageDtoPage.getTotalElements() != messageDtoPage.getNumberOfElements()) {
      return new ResponseEntity<>(messageDtoPage, HttpStatus.PARTIAL_CONTENT);
    }
    return new ResponseEntity<>(messageDtoPage, HttpStatus.OK);
  }

}
