package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model;

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
@Table(name = "partner")
public class Partner {
  @Id
  @SequenceGenerator(name = "partner_seq", sequenceName = "partner_seq", initialValue = 100, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
  private Long id;

  /**
   * Alias of the partner
   */
  @Column(nullable = false)
  private String alias;
}
