package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.exception.ResourceNotFoundException;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service.PartnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartnerController.class)
class PartnerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PartnerService partnerService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getPartners_shouldReturnPage() throws Exception {
    // Given
    PartnerDTO partner = new PartnerDTO();
    partner.setId(1L);
    partner.setAlias("test-alias");

    when(partnerService.getPartnerPage(0, 10))
      .thenReturn(new PageImpl<>(List.of(partner), PageRequest.of(0, 10), 1));

    // When
    mockMvc.perform(get("/api/partners?page=0&size=10"))

      // Then
      .andExpect(status().isOk())
      .andExpect(jsonPath("content[0].id", is(1)))
      .andExpect(jsonPath("content[0].alias", is("test-alias")));
  }

  @Test
  void getById_shouldReturnPartner() throws Exception {
    // Given
    PartnerDTO partner = new PartnerDTO();
    partner.setId(1L);
    partner.setAlias("test-alias");

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
    PartnerDTO partner = new PartnerDTO();
    partner.setId(1L);
    partner.setAlias("alias-x");

    when(partnerService.getByAlias("alias-x")).thenReturn(partner);

    // When
    mockMvc.perform(get("/api/partners/alias/alias-x"))

      // Then
      .andExpect(status().isOk())
      .andExpect(jsonPath("alias", is("alias-x")));
  }

  @Test
  void create_shouldCallService() throws Exception {
    // Given
    CreatePartnerDTO dto = new CreatePartnerDTO();
    dto.setAlias("created-alias");

    // When
    mockMvc.perform(post("/api/partners")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))

      // Then
      .andExpect(status().isOk());

    verify(partnerService).create(any(CreatePartnerDTO.class));
  }

  @Test
  void update_shouldCallService() throws Exception {
    // Given
    CreatePartnerDTO dto = new CreatePartnerDTO();
    dto.setAlias("updated-alias");

    // When
    mockMvc.perform(put("/api/partners/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))

      // Then
      .andExpect(status().isOk());

    verify(partnerService).update(eq(1L), any(CreatePartnerDTO.class));
  }

  @Test
  void delete_shouldCallService() throws Exception {
    // Given When
    mockMvc.perform(delete("/api/partners/1"))

      // Then
      .andExpect(status().isOk());

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
  void getByAlias_shouldReturn400_whenAliasInvalid() throws Exception {
    // Given
    when(partnerService.getByAlias("invalid-alias"))
      .thenThrow(new IllegalArgumentException("Invalid alias format"));

    // When
    mockMvc.perform(get("/api/partners/alias/invalid-alias"))

      // Then
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.status", is(400)))
      .andExpect(jsonPath("$.error", is("Bad Request")));
  }

  @Test
  void update_shouldReturn500_whenUnhandledException() throws Exception {
    // Given
    CreatePartnerDTO dto = new CreatePartnerDTO();
    dto.setAlias("crash");

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
