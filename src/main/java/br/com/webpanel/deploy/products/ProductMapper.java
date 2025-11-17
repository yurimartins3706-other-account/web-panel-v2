package br.com.webpanel.deploy.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.webpanel.deploy.products.dto.CreateProductDto;
import br.com.webpanel.deploy.products.dto.RecoveryProductDto;
import br.com.webpanel.deploy.products.dto.RecoveryProductCustomAttributeDto;

/**
 * Mapper implemented by MapStruct at build time.
 * componentModel = "spring" makes the generated mapper a Spring bean and
 * NullValuePropertyMappingStrategy.IGNORE keeps existing values when updating
 * and the source property is null (useful for partial updates).
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    /**
     * Convert a {@link Product} entity to a {@link RecoveryProductDto}.
     * Maps categories, custom attributes and audit fields.
     *
     * @param product the entity to map
     * @return a DTO representing the product
     */
    @Mapping(target = "categoryIds", expression = "java(product.getCategories() != null ? product.getCategories().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toSet()) : null)")
    @Mapping(target = "customAttributes", expression = "java(product.getCustomAttributeValues() != null ? product.getCustomAttributeValues().stream().map(this::customAttributeValueToDto).collect(java.util.stream.Collectors.toSet()) : null)")
    RecoveryProductDto toDto(Product product);

    /**
     * Convert a {@link CreateProductDto} to a {@link Product} entity.
     * Note: categories and custom attributes are handled separately in the service layer.
     *
     * @param dto the creation DTO
     * @return a mapped Product entity
     */
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "customAttributeValues", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(CreateProductDto dto);

    /**
     * Update an existing {@link Product} entity with data from a {@link CreateProductDto}.
     *
     * @param dto the DTO containing updated data
     * @param entity the entity to update
     */
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "customAttributeValues", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CreateProductDto dto, @MappingTarget Product entity);

    /**
     * Convert a {@link ProductCustomAttributeValue} to a {@link RecoveryProductCustomAttributeDto}.
     *
     * @param value the custom attribute value
     * @return a DTO representing the custom attribute
     */
    @Mapping(source = "customField.id", target = "customFieldId")
    @Mapping(source = "customField.fieldName", target = "fieldName")
    @Mapping(source = "customField.fieldLabel", target = "fieldLabel")
    RecoveryProductCustomAttributeDto customAttributeValueToDto(ProductCustomAttributeValue value);
}
