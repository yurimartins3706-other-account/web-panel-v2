package br.com.webpanel.deploy.customfields.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.webpanel.deploy.customfields.CustomField.FieldType;
import java.util.List;

/**
 * DTO for creating a new CustomField.
 */
public record CreateCustomFieldDto(
    @NotBlank(message = "fieldName is required")
    @Size(max = 255, message = "fieldName must not exceed 255 characters")
    String fieldName,

    @NotBlank(message = "fieldLabel is required")
    @Size(max = 255, message = "fieldLabel must not exceed 255 characters")
    String fieldLabel,

    @NotNull(message = "fieldType is required")
    FieldType fieldType,

    Boolean isRequired,

    Integer maxLength,

    List<String> arrayOptions,

    String defaultValue
) {}
