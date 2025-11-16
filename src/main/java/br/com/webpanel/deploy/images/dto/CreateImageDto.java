package br.com.webpanel.deploy.images.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new Image.
 */
public record CreateImageDto(
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must not exceed 255 characters")
    String name,
    
    @NotBlank(message = "base64 content is required")
    String base64,
    
    @NotNull(message = "size is required")
    Long size
) {}