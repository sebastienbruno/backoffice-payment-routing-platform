package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.exception.ResourceNotFoundException;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.mapper.PartnerMapper;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model.Partner;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.repository.PartnerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PartnerService {

  private final PartnerRepository repository;
  private final PartnerMapper mapper;

  public PartnerService(PartnerRepository repository, PartnerMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public Page<PartnerDTO> getPartnerPage(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page index must be >= 0 and size > 0");
    }

    try {
      Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
      Page<Partner> partnersPage = repository.findAll(pageable);
      List<PartnerDTO> dtoList = mapper.listPartnerToListPartnerDto(partnersPage.getContent());
      return new PageImpl<>(dtoList, pageable, partnersPage.getTotalElements());
    } catch (Exception ex) {
      log.error("Failed to retrieve partners page [page={}, size={}]", page, size, ex);
      throw new IllegalStateException(
        String.format("Database error when fetching partners (page=%d, size=%d)", page, size), ex
      );
    }
  }

  public void create(CreatePartnerDTO createPartnerDto) {
    this.repository.save(this.mapper.createPartnerDtoToPartner(createPartnerDto));
  }

  public PartnerDTO getById(Long id) {
    Partner partner = repository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + id));
    return mapper.partnerToPartnerDto(partner);
  }

  public PartnerDTO getByAlias(String alias) {
    Partner partner = repository.findByAlias(alias)
      .orElseThrow(() -> new ResourceNotFoundException("Partner not found with alias: " + alias));
    return mapper.partnerToPartnerDto(partner);
  }

  public void update(Long id, CreatePartnerDTO dto) {
    Partner existingPartner = repository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + id));

    Partner updatedPartner = mapper.createPartnerDtoToPartner(dto);
    updatedPartner.setId(existingPartner.getId());

    repository.save(updatedPartner);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Cannot delete non-existent partner with id: " + id);
    }
    repository.deleteById(id);
  }
}
