package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.controller;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partners")
@Log4j2
public class PartnerController {

  private final PartnerService service;

  public PartnerController(PartnerService service) {
    this.service = service;
  }

  @Operation(
    summary = "Retrieve a paginated list of partners",
    description = "Returns partners. Pagination is supported using `page` and `size` query parameters."
  )
  @ApiResponse(responseCode = "200", description = "All partners returned")
  @ApiResponse(responseCode = "206", description = "Partial content returned (there are more pages)")
  @Parameter(name = "page", description = "Page number to retrieve (0-based)", example = "0")
  @Parameter(name = "size", description = "Number of elements per page", example = "10")
  @GetMapping
  public ResponseEntity<Page<PartnerDTO>> getPartners(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    Page<PartnerDTO> partnerDtoPage = service.getPartnerPage(page, size);
    HttpStatus status = partnerDtoPage.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;
    return ResponseEntity.status(status)
      .header("X-Total-Count", String.valueOf(partnerDtoPage.getTotalElements()))
      .header("X-Page-Number", String.valueOf(partnerDtoPage.getNumber()))
      .header("X-Page-Size", String.valueOf(partnerDtoPage.getSize()))
      .body(partnerDtoPage);
  }
}
