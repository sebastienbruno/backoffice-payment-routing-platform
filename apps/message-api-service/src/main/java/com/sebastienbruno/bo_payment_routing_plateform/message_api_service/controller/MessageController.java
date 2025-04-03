package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.controller;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.config.JmsConfig;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@Log4j2
public class MessageController {

  private final MessageService service;
  private final JmsTemplate jmsTemplate;

  public MessageController(MessageService service, JmsTemplate jmsTemplate) {
    this.service = service;
    this.jmsTemplate = jmsTemplate;
  }

  @Operation(
    summary = "Retrieve a paginated list of messages",
    description = "Returns messages previously received from the MQ queue and stored in the database. Pagination is supported using `page` and `size` query parameters."
  )
  @ApiResponse(responseCode = "200", description = "All messages returned")
  @ApiResponse(responseCode = "206", description = "Partial content returned (there are more pages)")
  @Parameter(name = "page", description = "Page number to retrieve (0-based)", example = "0")
  @Parameter(name = "size", description = "Number of elements per page", example = "10")
  @GetMapping
  public ResponseEntity<Page<MessageDTO>> getMessages(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    Page<MessageDTO> messageDtoPage = service.getMessagePage(page, size);
    HttpStatus status = messageDtoPage.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;
    return ResponseEntity.status(status)
      .header("X-Total-Count", String.valueOf(messageDtoPage.getTotalElements()))
      .header("X-Page-Number", String.valueOf(messageDtoPage.getNumber()))
      .header("X-Page-Size", String.valueOf(messageDtoPage.getSize()))
      .body(messageDtoPage);
  }

  @Operation(
    summary = "Send a mock message to the MQ queue",
    description = "Sending a structured message to the MQ queue for testing purposes.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      required = true,
      description = "The message payload to be sent to MQ as JSON"
    )
  )
  @ApiResponse(responseCode = "200", description = "Message successfully sent")
  @ApiResponse(responseCode = "503", description = "MQ unavailable")
  @PostMapping("mock/send")
  public ResponseEntity<String> sendMessage(@Valid @RequestBody CreateMessageDTO createMessageDTO){
    try{
      log.info("Sending message to MQ: {}", createMessageDTO);
      jmsTemplate.convertAndSend(JmsConfig.MESSAGE_QUEUE, createMessageDTO);
      return ResponseEntity.ok("OK");
    }catch(JmsException ex){
      log.error("Failed to send message to MQ", ex);
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("MQ unavailable");
    }
  }


}
