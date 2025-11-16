package br.com.webpanel.deploy.images;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;

/**
 * Mapper implemented by MapStruct at build time.
 * componentModel = "spring" makes the generated mapper a Spring bean and
 * NullValuePropertyMappingStrategy.IGNORE keeps existing values when updating
 * and the source property is null (useful for partial updates).
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ImageMapper {

    /**
     * Convert an {@link Image} entity to a {@link RecoveryImageDto}.
     *
     * @param image the entity to map
     * @return a DTO representing the image
     */
    RecoveryImageDto toDto(Image image);

    /**
     * Convert a {@link CreateImageDto} to an {@link Image} entity.
     *
     * @param dto the creation DTO
     * @return a mapped Image entity
     */
    Image toEntity(CreateImageDto dto);

    /**
     * Update an existing {@link Image} entity with data from a {@link CreateImageDto}.
     *
     * @param dto the DTO containing updated data
     * @param entity the entity to update
     */
    void updateEntityFromDto(CreateImageDto dto, @MappingTarget Image entity);
}