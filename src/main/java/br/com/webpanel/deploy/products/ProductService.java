package br.com.webpanel.deploy.products;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.webpanel.deploy.categories.Category;
import br.com.webpanel.deploy.categories.CategoryRepository;
import br.com.webpanel.deploy.customfields.CustomField;
import br.com.webpanel.deploy.customfields.CustomFieldRepository;
import br.com.webpanel.deploy.products.dto.CreateProductDto;
import br.com.webpanel.deploy.products.dto.CreateProductCustomAttributeDto;
import br.com.webpanel.deploy.products.dto.RecoveryProductDto;

/**
 * Service layer for Product CRUD operations.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final CustomFieldRepository customFieldRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                         CategoryRepository categoryRepository, CustomFieldRepository customFieldRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.customFieldRepository = customFieldRepository;
    }

    /**
     * Creates a new product from the provided DTO.
     *
     * @param dto the DTO containing product data
     * @return the created product as a RecoveryProductDto
     * @throws ResponseStatusException with status 409 if SKU already exists
     * @throws ResponseStatusException with status 404 if any category or custom field is not found
     */
    public RecoveryProductDto create(CreateProductDto dto) {
        // Check if SKU already exists
        if (productRepository.existsBySkuIgnoreCase(dto.sku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "A product with SKU '" + dto.sku() + "' already exists");
        }

        Product entity = productMapper.toEntity(dto);

        // Set categories
        if (dto.categoryIds() != null && !dto.categoryIds().isEmpty()) {
            Set<Category> categories = categoryRepository.findAllById(dto.categoryIds())
                .stream()
                .collect(Collectors.toSet());

            if (categories.size() != dto.categoryIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more categories not found");
            }
            entity.setCategories(categories);
        }

        Product saved = productRepository.save(entity);

        // Set custom attributes
        if (dto.customAttributes() != null && !dto.customAttributes().isEmpty()) {
            Set<ProductCustomAttributeValue> attributeValues = dto.customAttributes().stream()
                .map(attr -> createCustomAttributeValue(saved, attr))
                .collect(Collectors.toSet());
            saved.setCustomAttributeValues(attributeValues);
        }

        Product updatedProduct = productRepository.save(saved);
        return productMapper.toDto(updatedProduct);
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products as RecoveryProductDto
     */
    @Transactional(readOnly = true)
    public List<RecoveryProductDto> getAll() {
        return productRepository.findAll().stream()
            .map(productMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return the product as a RecoveryProductDto
     * @throws ResponseStatusException with status 404 if product is not found
     */
    @Transactional(readOnly = true)
    public RecoveryProductDto getById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return productMapper.toDto(product);
    }

    /**
     * Updates an existing product with the provided DTO data.
     *
     * @param id the product ID
     * @param dto the DTO containing updated product data
     * @return the updated product as a RecoveryProductDto
     * @throws ResponseStatusException with status 404 if product is not found
     * @throws ResponseStatusException with status 409 if new SKU already exists
     * @throws ResponseStatusException with status 404 if any category or custom field is not found
     */
    public RecoveryProductDto update(Long id, CreateProductDto dto) {
        Product existing = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // Check if new SKU already exists (ignoring current product)
        if (!existing.getSku().equalsIgnoreCase(dto.sku()) &&
            productRepository.existsBySkuIgnoreCase(dto.sku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "A product with SKU '" + dto.sku() + "' already exists");
        }

        productMapper.updateEntityFromDto(dto, existing);

        // Update categories
        if (dto.categoryIds() != null) {
            Set<Category> categories = categoryRepository.findAllById(dto.categoryIds())
                .stream()
                .collect(Collectors.toSet());

            if (categories.size() != dto.categoryIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more categories not found");
            }
            existing.setCategories(categories);
        }

        Product saved = productRepository.save(existing);

        // Update custom attributes
        if (dto.customAttributes() != null) {
            saved.setCustomAttributeValues(dto.customAttributes().stream()
                .map(attr -> createCustomAttributeValue(saved, attr))
                .collect(Collectors.toSet()));
        }

        Product updatedProduct = productRepository.save(saved);
        return productMapper.toDto(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID
     * @throws ResponseStatusException with status 404 if product is not found
     */
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }

    /**
     * Helper method to create a ProductCustomAttributeValue from a DTO.
     *
     * @param product the product entity
     * @param dto the custom attribute DTO
     * @return a ProductCustomAttributeValue
     * @throws ResponseStatusException with status 404 if custom field is not found
     */
    private ProductCustomAttributeValue createCustomAttributeValue(Product product, CreateProductCustomAttributeDto dto) {
        CustomField customField = customFieldRepository.findById(dto.customFieldId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Custom field with ID " + dto.customFieldId() + " not found"));

        return ProductCustomAttributeValue.builder()
            .product(product)
            .customField(customField)
            .value(dto.value())
            .build();
    }
}
