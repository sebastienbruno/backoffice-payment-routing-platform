package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.repository;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}
