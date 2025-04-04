package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Direction of the flow")
public enum Direction {

  @Schema(description = "Incoming message flow")
  INBOUND,

  @Schema(description = "Outgoing message flow")
  OUTBOUND
}
