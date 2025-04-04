package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.repository;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {}
