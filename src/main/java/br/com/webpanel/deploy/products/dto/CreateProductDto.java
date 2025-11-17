package br.com.webpanel.deploy.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * DTO for creating a new Product.
 */
public record CreateProductDto(
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must not exceed 255 characters")
    String name,

    @NotBlank(message = "sku is required")
    @Size(max = 100, message = "sku must not exceed 100 characters")
    String sku,

    @NotNull(message = "price is required")
    Double price,

    @Size(max = 255, message = "brand must not exceed 255 characters")
    String brand,

    String description,

    Set<Long> categoryIds,

    Set<CreateProductCustomAttributeDto> customAttributes
) {}
