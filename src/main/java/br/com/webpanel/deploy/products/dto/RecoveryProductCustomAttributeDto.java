package br.com.webpanel.deploy.products.dto;

/**
 * DTO for recovering a product's custom attribute value in responses.
 */
public record RecoveryProductCustomAttributeDto(
    Long id,
    Long customFieldId,
    String fieldName,
    String fieldLabel,
    String value
) {}
