package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
