package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums.Direction;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.enums.ProcessedFlowType;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.exception.ResourceNotFoundException;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service.PartnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartnerController.class)
class PartnerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PartnerService partnerService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getPartners_shouldReturnAll() throws Exception {
    // Given
    PartnerDTO partner = PartnerDTO.builder()
      .id(1L)
      .alias("test-alias")
      .build();

    when(partnerService.getPartners())
      .thenReturn(List.of(partner));

    // When
    mockMvc.perform(get("/api/partners"))

      // Then
      .andExpect(status().isOk())
      .andExpect(header().string("X-Total-Count", "1"))
      .andExpect(jsonPath("$[0].id", is(1)))
      .andExpect(jsonPath("$[0].alias", is("test-alias")));
  }

  @Test
  void getPartners_shouldReturn206_whenTooManyResults() throws Exception {
    // Given
    List<PartnerDTO> all =
      java.util.stream.IntStream.rangeClosed(1, 1001)
        .mapToObj(i -> PartnerDTO.builder()
            .id((long) i)
            .alias("alias-" + i)
            .build()
        ).toList();

    when(partnerService.getPartners()).thenReturn(all);

    // When
    mockMvc.perform(get("/api/partners"))

      // Then
      .andExpect(status().isPartialContent())
      .andExpect(header().string("X-Total-Count", "1001"))
      .andExpect(header().string("X-Content-Note", org.hamcrest.Matchers.containsString("truncated")))
      .andExpect(jsonPath("$.length()", is(1000)));
  }

  @Test
  void getById_shouldReturnPartner() throws Exception {
    // Given
    PartnerDTO partner = PartnerDTO.builder()
      .id(1L)
      .alias("test-alias")
      .build();

    when(partnerService.getById(1L)).thenReturn(partner);

    // When
    mockMvc.perform(get("/api/partners/1"))

      // Then
      .andExpect(status().isOk())
      .andExpect(jsonPath("id", is(1)))
      .andExpect(jsonPath("alias", is("test-alias")));
  }

  @Test
  void getByAlias_shouldReturnPartner() throws Exception {
    // Given
    PartnerDTO partner = PartnerDTO.builder()
      .id(1L)
      .alias("alias-x")
      .build();

    when(partnerService.findByAlias("alias-x")).thenReturn(Optional.of(partner));

    // When
    mockMvc.perform(get("/api/partners?alias=alias-x"))

      // Then
      .andExpect(status().isOk())
      .andExpect(header().string("X-Total-Count", "1"))
      .andExpect(jsonPath("$[0].id", is(1)))
      .andExpect(jsonPath("$[0].alias", is("alias-x")));
  }

  @Test
  void create_shouldReturnCreatedStatusAndLocation() throws Exception {
    // Given
    CreatePartnerDTO dto = CreatePartnerDTO.builder()
      .alias("created-alias")
      .type("created-type")
      .direction(Direction.INBOUND)
      .description("created-description")
      .processedFlowType(ProcessedFlowType.MESSAGE)
      .build();

    PartnerDTO created = PartnerDTO.builder()
      .id(42L)
      .alias("created-alias")
      .type("created-type")
      .direction(Direction.INBOUND)
      .description("created-description")
      .processedFlowType(ProcessedFlowType.MESSAGE)
      .build();

    when(partnerService.create(any())).thenReturn(created);

    // When
    mockMvc.perform(post("/api/partners")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))

      // Then
      .andExpect(status().isCreated())
      .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/partners/42")));

    verify(partnerService).create(any(CreatePartnerDTO.class));
  }

  @Test
  void update_shouldCallService() throws Exception {
    // Given
    PartnerDTO dto = PartnerDTO.builder()
      .id(1L)
      .alias("updated-alias")
      .build();

    // When
    mockMvc.perform(put("/api/partners/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))

      // Then
      .andExpect(status().isNoContent());

    verify(partnerService).update(eq(1L), any(PartnerDTO.class));
  }

  @Test
  void delete_shouldCallService() throws Exception {
    // Given When
    mockMvc.perform(delete("/api/partners/1"))

      // Then
      .andExpect(status().isNoContent());

    verify(partnerService).delete(1L);
  }

  // --- GlobalExceptionHandler ---

  @Test
  void getById_shouldReturn404_whenNotFound() throws Exception {
    // Given
    when(partnerService.getById(999L))
      .thenThrow(new ResourceNotFoundException("Partner not found with id: 999"));

    // When
    mockMvc.perform(get("/api/partners/999"))

      // Then
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.status", is(404)))
      .andExpect(jsonPath("$.error", is("Not Found")));
  }

  @Test
  void getByAlias_shouldReturnOKAndEmptyList_whenAliasInvalid() throws Exception {
    // Given
    when(partnerService.findByAlias("invalid-alias")).thenReturn(Optional.empty());

    // When
    mockMvc.perform(get("/api/partners?alias=invalid-alias"))

      // Then
      .andExpect(status().isOk())
      .andExpect(header().string("X-Total-Count", "0"));
  }

  @Test
  void update_shouldReturn500_whenUnhandledException() throws Exception {
    // Given
    CreatePartnerDTO dto = CreatePartnerDTO.builder()
      .alias("crash")
      .build();

    doThrow(new RuntimeException("Unexpected DB error"))
      .when(partnerService).update(eq(999L), any());

    // When
    mockMvc.perform(put("/api/partners/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))

      // Then
      .andExpect(status().isInternalServerError())
      .andExpect(jsonPath("$.status", is(500)))
      .andExpect(jsonPath("$.error", is("Internal Server Error")));
  }
}
