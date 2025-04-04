package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.mapper;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model.Partner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

  Partner partnerDtoToPartner(PartnerDTO partnerDTO);
  Partner createPartnerDtoToPartner(CreatePartnerDTO createPartnerDTO);
  PartnerDTO partnerToPartnerDto(Partner partner);

  List<Partner> listPartnerDtoToListPartner(List<PartnerDTO> listPartnerDto);
  List<PartnerDTO> listPartnerToListPartnerDto(List<Partner> listPartner);
}
