package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.exception.ResourceNotFoundException;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.mapper.PartnerMapper;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.model.Partner;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class PartnerServiceTest {

  @Mock
  private PartnerRepository repository;

  @Mock
  private PartnerMapper mapper;

  @InjectMocks
  private PartnerService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getPartnerPage_shouldReturnMappedPage() {
    // Given
    Partner partner = new Partner();
    PartnerDTO partnerDTO = new PartnerDTO();

    when(repository.findAll(any(Pageable.class)))
      .thenReturn(new PageImpl<>(List.of(partner)));

    when(mapper.listPartnerToListPartnerDto(List.of(partner)))
      .thenReturn(List.of(partnerDTO));

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
  void create_shouldMapAndSavePartner() {
    // Given
    CreatePartnerDTO dto = new CreatePartnerDTO();
    Partner partner = new Partner();

    when(mapper.createPartnerDtoToPartner(dto)).thenReturn(partner);

    // When
    service.create(dto);

    // Then
    verify(repository).save(partner);
  }

  @Test
  void getById_shouldReturnPartnerDTO_whenFound() {
    // Given
    Partner partner = new Partner();
    PartnerDTO dto = new PartnerDTO();

    when(repository.findById(1L)).thenReturn(Optional.of(partner));
    when(mapper.partnerToPartnerDto(partner)).thenReturn(dto);

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
  void getByAlias_shouldReturnPartnerDTO_whenFound() {
    // Given
    Partner partner = new Partner();
    PartnerDTO dto = new PartnerDTO();

    when(repository.findByAlias("alias")).thenReturn(Optional.of(partner));
    when(mapper.partnerToPartnerDto(partner)).thenReturn(dto);

    // When
    PartnerDTO result = service.getByAlias("alias");

    // Then
    assertThat(result).isEqualTo(dto);
  }

  @Test
  void getByAlias_shouldThrow_whenNotFound() {
    // Given
    when(repository.findByAlias("alias")).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> service.getByAlias("alias"))
      .isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void update_shouldMapAndSaveUpdatedPartner_whenExists() {
    // Given
    Partner existing = new Partner();
    existing.setId(1L);
    CreatePartnerDTO dto = new CreatePartnerDTO();
    Partner mapped = new Partner();

    when(repository.findById(1L)).thenReturn(Optional.of(existing));
    when(mapper.createPartnerDtoToPartner(dto)).thenReturn(mapped);

    // When
    service.update(1L, dto);

    // Then
    assertThat(mapped.getId()).isEqualTo(1L);
    verify(repository).save(mapped);
  }

  @Test
  void update_shouldThrow_whenNotFound() {
    // Given
    when(repository.findById(1L)).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> service.update(1L, new CreatePartnerDTO()))
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
