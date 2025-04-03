package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  description = "Data Transfer Object for a message",
  example = """
    {
      "id": 123,
      "sender": "backoffice-app",
      "recipients": ["partner-a", "partner-b"],
      "payload": "ALERT|LEVEL:HIGH|TIMESTAMP:2025-04-02T11:30:00"
    }
  """
)
public class MessageDTO {

  @Schema(description = "Unique identifier of the message", example = "123")
  private Long id;

  @Schema(description = "Name of the sender application", example = "backoffice-app")
  private String sender;

  @Schema(description = "List of message recipients", example = "[\"partner-a\", \"partner-b\"]")
  private List<@NotBlank String> recipients;

  @Schema(description = "Payload content of the message", example = "ALERT|LEVEL:HIGH|TIMESTAMP:2025-04-02T11:30:00")
  private String payload;
}
