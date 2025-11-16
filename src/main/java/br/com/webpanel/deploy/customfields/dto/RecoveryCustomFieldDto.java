package br.com.webpanel.deploy.customfields.dto;

import java.time.Instant;
import java.util.List;
import br.com.webpanel.deploy.customfields.CustomField.FieldType;

/**
 * DTO returned to clients with audit timestamps.
 */
public record RecoveryCustomFieldDto(
    Long id,
    String fieldName,
    String fieldLabel,
    FieldType fieldType,
    Boolean isRequired,
    Integer maxLength,
    List<String> arrayOptions,
    String defaultValue,
    Instant createdAt,
    Instant updatedAt
) {}
