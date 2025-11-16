package br.com.webpanel.deploy.customfields;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.webpanel.deploy.customfields.dto.CreateCustomFieldDto;
import br.com.webpanel.deploy.customfields.dto.RecoveryCustomFieldDto;

@RestController
@RequestMapping(value = "/api/custom-field", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Custom Fields", description = "Custom field management endpoints")
public class CustomFieldController {

    private final CustomFieldService service;

    public CustomFieldController(CustomFieldService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new custom field")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Custom field created"),
        @ApiResponse(responseCode = "400", description = "Invalid input - fieldName or fieldLabel is empty, or fieldType is invalid"),
        @ApiResponse(responseCode = "409", description = "Custom field name already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryCustomFieldDto> create(@Valid @RequestBody CreateCustomFieldDto dto) {
        RecoveryCustomFieldDto created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "List all custom fields")
    @ApiResponse(responseCode = "200", description = "Custom fields found")
    @GetMapping
    public ResponseEntity<List<RecoveryCustomFieldDto>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get a custom field by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Custom field found"),
        @ApiResponse(responseCode = "404", description = "Custom field not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecoveryCustomFieldDto> getById(
            @Parameter(description = "Custom Field ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update a custom field")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Custom field updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Custom field not found"),
        @ApiResponse(responseCode = "409", description = "Custom field name already exists")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryCustomFieldDto> update(
            @Parameter(description = "Custom Field ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CreateCustomFieldDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete a custom field")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Custom field deleted"),
        @ApiResponse(responseCode = "404", description = "Custom field not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Custom Field ID", example = "1")
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
