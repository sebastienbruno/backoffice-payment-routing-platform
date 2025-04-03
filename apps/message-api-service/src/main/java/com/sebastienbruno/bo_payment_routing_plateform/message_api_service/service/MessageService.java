package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.mapper.MessageMapper;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.model.Message;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repository;
    private final MessageMapper mapper;

    public MessageService(MessageRepository repository, MessageMapper mapper) {
      this.repository = repository;
      this.mapper = mapper;
    }

    public Page<MessageDTO> getMessagePage(
      int page,
      int size
    ) {
      Pageable pageable = PageRequest.of(page, size, Sort.by("receivedAt").descending());
      Page<Message> messagesPage = repository.findAll(pageable);
      List<MessageDTO> dtoList = mapper.listMessageToListMessageDto(messagesPage.getContent());

      return new PageImpl<>(dtoList, pageable, messagesPage.getTotalElements());
    }
}
