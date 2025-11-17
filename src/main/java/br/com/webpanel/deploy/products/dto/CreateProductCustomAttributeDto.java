package br.com.webpanel.deploy.products.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a custom attribute value for a product.
 */
public record CreateProductCustomAttributeDto(
    @NotNull(message = "customFieldId is required")
    Long customFieldId,

    @NotBlank(message = "value is required")
    String value
) {}
