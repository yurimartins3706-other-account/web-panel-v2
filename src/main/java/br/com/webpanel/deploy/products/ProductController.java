package br.com.webpanel.deploy.products;

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

import br.com.webpanel.deploy.products.dto.CreateProductDto;
import br.com.webpanel.deploy.products.dto.RecoveryProductDto;

@RestController
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created"),
        @ApiResponse(responseCode = "400", description = "Invalid input - name, sku or price is empty or invalid"),
        @ApiResponse(responseCode = "404", description = "One or more categories or custom fields not found"),
        @ApiResponse(responseCode = "409", description = "Product SKU already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryProductDto> create(@Valid @RequestBody CreateProductDto dto) {
        RecoveryProductDto created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "List all products")
    @ApiResponse(responseCode = "200", description = "Products found")
    @GetMapping
    public ResponseEntity<List<RecoveryProductDto>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecoveryProductDto> getById(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Product not found or one or more categories/custom fields not found"),
        @ApiResponse(responseCode = "409", description = "Product SKU already exists")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryProductDto> update(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CreateProductDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
