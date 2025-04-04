package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for message creation")
public class CreateMessageDTO {

  @NotBlank(message = "Sender must not be blank")
  @Schema(description = "Name of the sender application", example = "backoffice-app")
  private String sender;

  @NotEmpty(message = "At least one recipient is required")
  @Schema(description = "List of message recipients", example = "[\"partner-a\", \"partner-b\"]")
  private List<@NotBlank String> recipients;

  @NotBlank(message = "Payload must not be blank")
  @Schema(description = "Payload content of the message", example = "ALERT|LEVEL:HIGH|TIMESTAMP:2025-04-02T11:30:00")
  private String payload;
}
