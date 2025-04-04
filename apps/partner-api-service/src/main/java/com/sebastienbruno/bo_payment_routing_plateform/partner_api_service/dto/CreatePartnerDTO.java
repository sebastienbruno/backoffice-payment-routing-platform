package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object to create a partner")
public class CreatePartnerDTO {

  @NotBlank(message = "Alias must not be blank")
  @Schema(description = "Alias of the partner", example = "backoffice-app")
  private String alias;
}
