package com.sebastienbruno.bo_payment_routing_plateform.message_ingest_service.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Logical sender of the message (e.g., alias, partner name, or system ID)
   */
  private String sender;

  /**
   * One or multiple recipients
   */
  @ElementCollection
  @CollectionTable(name = "message_recipients", joinColumns = @JoinColumn(name = "message_id"))
  @Column(name = "recipient")
  private List<String> recipients;

  /**
   * Raw message payload (XML, JSON, etc.)
   */
  @Lob
  private String payload;

  /**
   * Timestamp when the message was received
   */
  private LocalDateTime receivedAt;
}
