package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.exception.ResourceNotFoundException;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.mapper.PartnerMapper;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model.Partner;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class PartnerServiceTest {

  private PartnerRepository repository;
  private PartnerMapper mapper;
  private PartnerService service;

  @BeforeEach
  void setUp() {
    repository = mock(PartnerRepository.class);
    mapper = Mappers.getMapper(PartnerMapper.class);
    service = new PartnerService(repository, mapper);
  }

  @Test
  void getPartners_shouldReturnListOfPartnerDTOs() {
    // Given
    Partner partner = new Partner();
    PartnerDTO dto = new PartnerDTO();

    when(repository.findAll()).thenReturn(List.of(partner));

    // When
    List<PartnerDTO> result = service.getPartners();

    // Then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(dto);
  }

  @Test
  void getPartnerPage_shouldReturnPage() {
    // Given
    Partner partner = new Partner();
    PartnerDTO partnerDTO = new PartnerDTO();

    when(repository.findAll(any(Pageable.class)))
      .thenReturn(new PageImpl<>(List.of(partner)));

    // When
    Page<PartnerDTO> result = service.getPartnerPage(0, 10);

    // Then
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(partnerDTO);
  }

  @Test
  void getPartnerPage_shouldThrowIllegalArgumentException_whenInvalidPageOrSize() {
    // When + Then
    assertThatThrownBy(() -> service.getPartnerPage(-1, 10))
      .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> service.getPartnerPage(0, 0))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void create_shouldSavePartner() {
    // Given
    CreatePartnerDTO dto = CreatePartnerDTO.builder()
      .alias("new-alias")
      .build();

    Partner saved = new Partner();
    saved.setId(42L);
    saved.setAlias("new-alias");

    when(repository.save(any())).thenReturn(saved);

    // When
    PartnerDTO result = service.create(dto);

    // Then
    assertThat(result.getId()).isEqualTo(42L);
    assertThat(result.getAlias()).isEqualTo("new-alias");
  }

  @Test
  void getById_shouldReturnPartnerDTO_whenFound() {
    // Given
    Partner partner = new Partner();
    partner.setId(1L);
    PartnerDTO dto = PartnerDTO.builder()
      .id(1L)
      .build();

    when(repository.findById(1L)).thenReturn(Optional.of(partner));

    // When
    PartnerDTO result = service.getById(1L);

    // Then
    assertThat(result).isEqualTo(dto);
  }

  @Test
  void getById_shouldThrow_whenNotFound() {
    // Given
    when(repository.findById(1L)).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> service.getById(1L))
      .isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void findByAlias_shouldReturnPartnerDTO_whenFound() {
    // Given
    Partner partner = new Partner();
    partner.setAlias("search-by-alias");
    PartnerDTO dto = new PartnerDTO();
    dto.setAlias("search-by-alias");

    when(repository.findByAlias("search-by-alias")).thenReturn(Optional.of(partner));

    // When
    Optional<PartnerDTO> result = service.findByAlias("search-by-alias");

    // Then
    assertThat(result.get()).isEqualTo(dto);
  }

  @Test
  void findByAlias_shouldReturnEmptyOptional_whenNotFound() {
    // Given
    when(repository.findByAlias("alias")).thenReturn(Optional.empty());

    // When
    Optional<PartnerDTO> result = service.findByAlias("alias");

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  void update_shouldSaveAndReturnPartnerDTO_whenExists() {
    // Given
    Long id = 1L;

    PartnerDTO dto = new PartnerDTO();
    dto.setId(id);
    dto.setAlias("updated-alias");

    Partner existing = new Partner();
    existing.setId(id);
    existing.setAlias("alias-to-update");

    Partner updated = new Partner();
    updated.setId(id);
    updated.setAlias("updated-alias");

    when(repository.findById(id)).thenReturn(Optional.of(existing));
    when(repository.save(any(Partner.class))).thenReturn(updated);

    // When
    PartnerDTO result = service.update(id, dto);

    // Then
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getAlias()).isEqualTo("updated-alias");
  }

  @Test
  void update_shouldThrow_whenNotFound() {
    // Given
    when(repository.findById(1L)).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> service.update(1L, new PartnerDTO()))
      .isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void delete_shouldCallDelete_whenExists() {
    // Given
    when(repository.existsById(1L)).thenReturn(true);

    // When
    service.delete(1L);

    // Then
    verify(repository).deleteById(1L);
  }

  @Test
  void delete_shouldThrow_whenNotExists() {
    // Given
    when(repository.existsById(1L)).thenReturn(false);

    // When + Then
    assertThatThrownBy(() -> service.delete(1L))
      .isInstanceOf(ResourceNotFoundException.class);
  }
}
