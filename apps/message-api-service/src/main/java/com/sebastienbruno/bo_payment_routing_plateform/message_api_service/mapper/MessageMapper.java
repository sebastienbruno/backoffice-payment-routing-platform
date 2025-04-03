package com.sebastienbruno.bo_payment_routing_plateform.message_api_service.mapper;

import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.CreateMessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.dto.MessageDTO;
import com.sebastienbruno.bo_payment_routing_plateform.message_api_service.model.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

  Message messageDtoToMessage(MessageDTO messageDTO);
  Message createMessageDtoToMessage(CreateMessageDTO createMessageDTO);
  MessageDTO messageToMessageDto(Message message);

  List<Message> listMessageDtoToListMessage(List<MessageDTO> listMessageDto);
  List<MessageDTO> listMessageToListMessageDto(List<Message> listMessage);
}
