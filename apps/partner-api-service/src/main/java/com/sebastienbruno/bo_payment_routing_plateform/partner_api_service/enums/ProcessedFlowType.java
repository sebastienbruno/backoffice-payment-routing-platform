package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of the processed flow")
public enum ProcessedFlowType {

  @Schema(description = "Standard message type")
  MESSAGE,

  @Schema(description = "Alerting mechanism")
  ALERTING,

  @Schema(description = "Notification-related message")
  NOTIFICATION
}
