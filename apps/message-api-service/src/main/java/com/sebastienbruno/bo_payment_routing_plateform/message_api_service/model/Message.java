package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "message")
public class Message {
  @Id
  @SequenceGenerator(name = "message_seq", sequenceName = "message_seq", initialValue = 10000, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
  private Long id;

  /**
   * Logical sender of the message (e.g., alias, partner name, or system ID)
   */
  @Column(nullable = false)
  private String sender;

  /**
   * One or multiple recipients
   */
  @ElementCollection
  @CollectionTable(name = "message_recipients", joinColumns = @JoinColumn(name = "message_id"))
  @Column(name = "recipient")
  @BatchSize(size = 20)
  private List<String> recipients;

  /**
   * Raw message payload (XML, JSON, etc.)
   */
  @Lob
  @Column(nullable = false, length = 65535) // 64 KB
  private String payload;

}
