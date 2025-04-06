package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.controller;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(
  name = "Partner Management",
  description = "Endpoints for managing MQ partners: create, read, update, delete, and search by alias"
)
@RestController
@RequestMapping("/api/partners")
public class PartnerController {

  private final PartnerService service;

  @Value("${partner.max-results:1000}")
  private int maxResults;

  public PartnerController(PartnerService service) {
    this.service = service;
  }

  @Operation(
    summary = "Retrieve the list of partners",
    description = "Returns the full list of MQ partners. If the total number of partners exceeds the configured limit, only a partial list is returned with HTTP 206 status.",
    responses = {
      @ApiResponse(responseCode = "200", description = "All partners returned"),
      @ApiResponse(responseCode = "206", description = "Partial content returned due to result limit exceeded")
    }
  )
  @GetMapping
  public ResponseEntity<List<PartnerDTO>> getPartners(@RequestParam(value = "alias", required = false) String alias) {
    List<PartnerDTO> partners;
    int total;

    if (alias != null) {
      Optional<PartnerDTO> maybePartner = service.findByAlias(alias);
      partners = maybePartner.map(List::of).orElse(List.of());
    } else {
      partners = service.getPartners();
    }
    total = partners.size();

    boolean isTruncated = total > maxResults;
    List<PartnerDTO> result = isTruncated ? partners.subList(0, maxResults) : partners;

    ResponseEntity.BodyBuilder response = isTruncated
      ? ResponseEntity.status(206)
      : ResponseEntity.ok();

    response.header("X-Total-Count", String.valueOf(total));

    if (isTruncated) {
      response.header("X-Content-Note", "Result set truncated to " + maxResults + " items.");
    }

    return response.body(result);
  }

  @Operation(
    summary = "Get a partner by ID",
    responses = {
      @ApiResponse(responseCode = "200", description = "Partner found"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @GetMapping("/{id}")
  public ResponseEntity<PartnerDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @Operation(
    summary = "Create a new partner",
    requestBody = @RequestBody(
      required = true,
      description = "Partner details to create",
      content = @Content(schema = @Schema(implementation = CreatePartnerDTO.class))
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Partner successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request payload")
    }
  )
  @PostMapping
  public ResponseEntity<PartnerDTO> create(@RequestBody CreatePartnerDTO dto) {
    PartnerDTO created = service.create(dto);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(created.getId())
      .toUri();

    return ResponseEntity.created(location).build();
  }

  @Operation(
    summary = "Update an existing partner",
    responses = {
      @ApiResponse(responseCode = "204", description = "Partner successfully updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request payload"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PartnerDTO dto) {
    service.update(id, dto);
    return ResponseEntity.noContent().build();
  }

  @Operation(
    summary = "Delete a partner by ID",
    responses = {
      @ApiResponse(responseCode = "204", description = "Partner successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
