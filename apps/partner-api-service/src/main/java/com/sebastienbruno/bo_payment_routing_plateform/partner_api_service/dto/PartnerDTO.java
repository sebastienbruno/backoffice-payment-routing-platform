package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto;

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
@Schema(description = "Data Transfer Object of a partner")
public class PartnerDTO {

  @Schema(description = "Unique identifier of the partner", example = "123")
  private Long id;


  @NotBlank(message = "Alias must not be blank")
  @Schema(description = "Alias of the partner", example = "backoffice-app")
  private String alias;
}
