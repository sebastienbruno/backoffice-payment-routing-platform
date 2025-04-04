package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums.Direction;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums.ProcessedFlowType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for partner creation")
public class CreatePartnerDTO {

  @NotBlank(message = "Alias must not be blank")
  @Schema(description = "Unique alias of the partner", example = "SwiftGateway")
  private String alias;

  @NotBlank(message = "Type must not be blank")
  @Schema(description = "Type of the partner (e.g. internal, external, system)", example = "external")
  private String type;

  @NotNull(message = "Direction must not be null")
  @Schema(description = "Direction of the flow", example = "OUTBOUND")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Direction direction;

  @Schema(description = "Related application name", example = "SWIFT-APP")
  private String application;

  @NotNull(message = "Processed flow type must not be null")
  @Schema(description = "Type of the processed flow", example = "MESSAGE")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ProcessedFlowType processedFlowType;

  @NotBlank(message = "Description must not be blank")
  @Schema(description = "Free-form description of the partner", example = "Partner for SWIFT transactions")
  private String description;
}
