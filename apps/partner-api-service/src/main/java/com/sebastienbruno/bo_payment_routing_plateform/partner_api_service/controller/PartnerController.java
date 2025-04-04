package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.controller;

import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.CreatePartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.dto.PartnerDTO;
import com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(
  name = "Partner Management",
  description = "Endpoints for managing MQ partners: create, read, update, delete, and search by alias"
)
@RestController
@RequestMapping("/api/partners")
public class PartnerController {

  private final PartnerService service;

  public PartnerController(PartnerService service) {
    this.service = service;
  }

  @Operation(
    summary = "Retrieve a paginated list of partners",
    description = "Returns partners. Pagination is supported using `page` and `size` query parameters.",
    parameters = {
      @Parameter(name = "page", description = "Page number to retrieve (0-based)", example = "0"),
      @Parameter(name = "size", description = "Number of elements per page", example = "10")
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "All partners returned"),
      @ApiResponse(responseCode = "206", description = "Partial content returned (there are more pages)")
    }
  )
  @GetMapping
  public Page<PartnerDTO> getPartners(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    return service.getPartnerPage(page, size);
  }

  @Operation(
    summary = "Get a partner by ID",
    description = "Returns a single partner by its unique database ID",
    parameters = {
      @Parameter(name = "id", description = "ID of the partner", example = "42")
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "Partner found"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @GetMapping("/{id}")
  public PartnerDTO getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @Operation(
    summary = "Get a partner by alias",
    description = "Returns a single partner by its unique alias",
    parameters = {
      @Parameter(name = "alias", description = "Alias of the partner", example = "partner-x")
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "Partner found"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @GetMapping("/alias/{alias}")
  public PartnerDTO getByAlias(@PathVariable String alias) {
    return service.getByAlias(alias);
  }

  @Operation(
    summary = "Create a new partner",
    description = "Creates a new partner using the provided details.",
    requestBody = @RequestBody(
      required = true,
      description = "Partner details to create",
      content = @Content(schema = @Schema(implementation = CreatePartnerDTO.class))
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Partner successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request payload")
    }
  )
  @PostMapping
  public void create(@RequestBody CreatePartnerDTO dto) {
    service.create(dto);
  }

  @Operation(
    summary = "Update an existing partner",
    description = "Updates an existing partner using the provided ID and details.",
    parameters = {
      @Parameter(name = "id", description = "ID of the partner to update", example = "42")
    },
    requestBody = @RequestBody(
      required = true,
      description = "Updated partner details",
      content = @Content(schema = @Schema(implementation = CreatePartnerDTO.class))
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Partner successfully updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request payload"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody CreatePartnerDTO dto) {
    service.update(id, dto);
  }

  @Operation(
    summary = "Delete a partner by ID",
    description = "Deletes the partner identified by its ID.",
    parameters = {
      @Parameter(name = "id", description = "ID of the partner to delete", example = "42")
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "Partner successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Partner not found")
    }
  )
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
