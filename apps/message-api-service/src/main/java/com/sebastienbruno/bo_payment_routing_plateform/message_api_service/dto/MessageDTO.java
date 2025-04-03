package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class MessageDTO {

    private Long id;
    private String sender;
    private List<String> recipients;
    private String payload;
    private LocalDateTime receivedAt;

}
