package br.com.webpanel.deploy.products.dto;

import java.time.Instant;
import java.util.Set;

/**
 * DTO returned to clients with product data including custom attributes and categories.
 */
public record RecoveryProductDto(
    Long id,
    String name,
    String sku,
    Double price,
    String brand,
    String description,
    Set<Long> categoryIds,
    Set<Long> imagesIds,
    Set<RecoveryProductCustomAttributeDto> customAttributes,
    Instant createdAt,
    Instant updatedAt
) {}
