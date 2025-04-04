package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums.Direction;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums.ProcessedFlowType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "partner")
public class Partner {
  @Id
  @SequenceGenerator(name = "partner_seq", sequenceName = "partner_seq", initialValue = 100, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
  private Long id;

  /**
   * Unique alias of the partner
   */
  @Column(nullable = false, unique = true)
  private String alias;

  /**
   * Type of partner (e.g. system, internal, external, etc.)
   */
  @Column(nullable = false)
  private String type;

  /**
   * Direction of the flow (INBOUND or OUTBOUND)
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Direction direction;

  /**
   * Name of the application related to the partner
   */
  @Column
  private String application;

  /**
   * Type of the processed flow (MESSAGE, ALERTING, NOTIFICATION)
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "processed_flow_type", nullable = false, length = 15)
  private ProcessedFlowType processedFlowType;

  /**
   * Free-form description
   */
  @Column(nullable = false)
  private String description;

}
