package br.com.webpanel.deploy.customfields;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.webpanel.deploy.customfields.dto.CreateCustomFieldDto;
import br.com.webpanel.deploy.customfields.dto.RecoveryCustomFieldDto;

/**
 * Mapper implemented by MapStruct at build time.
 * componentModel = "spring" makes the generated mapper a Spring bean and
 * NullValuePropertyMappingStrategy.IGNORE keeps existing values when updating
 * and the source property is null (useful for partial updates).
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomFieldMapper {

    /**
     * Convert a {@link CustomField} entity to a {@link RecoveryCustomFieldDto}.
     *
     * @param customField the entity to map
     * @return a DTO representing the custom field
     */
    RecoveryCustomFieldDto toDto(CustomField customField);

    /**
     * Convert a {@link CreateCustomFieldDto} to a {@link CustomField} entity.
     *
     * @param dto the creation DTO
     * @return a mapped CustomField entity
     */
    CustomField toEntity(CreateCustomFieldDto dto);

    /**
     * Update an existing {@link CustomField} entity with data from a {@link CreateCustomFieldDto}.
     *
     * @param dto the DTO containing updated data
     * @param entity the entity to update
     */
    void updateEntityFromDto(CreateCustomFieldDto dto, @MappingTarget CustomField entity);
}
