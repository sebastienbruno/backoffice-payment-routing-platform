package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.service;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.mapper.MessageMapper;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.model.Message;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
      if (page < 0 || size <= 0) {
        throw new IllegalArgumentException("Page index must be >= 0 and size > 0");
      }

      try {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Message> messagesPage = repository.findAll(pageable);
        List<MessageDTO> dtoList = mapper.listMessageToListMessageDto(messagesPage.getContent());
        return new PageImpl<>(dtoList, pageable, messagesPage.getTotalElements());
      } catch (Exception ex) {
        log.error("Failed to retrieve messages page [page={}, size={}]", page, size, ex);
        throw new IllegalStateException(
          String.format("Database error when fetching messages (page=%d, size=%d)", page, size), ex
        );
      }
    }

    public void create(CreateMessageDTO createMessageDto) {
      this.repository.save(this.mapper.createMessageDtoToMessage(createMessageDto));
    }
}
