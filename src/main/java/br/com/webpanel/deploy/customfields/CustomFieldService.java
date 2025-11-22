package br.com.webpanel.deploy.customfields;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import br.com.webpanel.deploy.customfields.dto.CreateCustomFieldDto;
import br.com.webpanel.deploy.customfields.dto.RecoveryCustomFieldDto;

/**
 * Service layer for CustomField CRUD operations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CustomFieldService {

    private final CustomFieldRepository repository;
    private final CustomFieldMapper mapper;

    /**
     * Creates a new custom field from the provided DTO.
     *
     * @param dto the DTO containing custom field data
     * @return the created custom field as a RecoveryCustomFieldDto
     * @throws ResponseStatusException with status 409 if fieldName already exists
     */
    public RecoveryCustomFieldDto create(CreateCustomFieldDto dto) {
        // Check if fieldName already exists
        if (repository.existsByFieldNameIgnoreCase(dto.fieldName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "A custom field with name '" + dto.fieldName() + "' already exists");
        }

        CustomField entity = mapper.toEntity(dto);
        CustomField saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Retrieves all custom fields from the database.
     *
     * @return a list of all custom fields as RecoveryCustomFieldDto
     */
    @Transactional(readOnly = true)
    public List<RecoveryCustomFieldDto> getAll() {
        return repository.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves a custom field by its ID.
     *
     * @param id the custom field ID
     * @return the custom field as a RecoveryCustomFieldDto
     * @throws ResponseStatusException with status 404 if custom field is not found
     */
    @Transactional(readOnly = true)
    public RecoveryCustomFieldDto getById(Long id) {
        CustomField cf = repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Custom field not found")
        );
        return mapper.toDto(cf);
    }

    /**
     * Updates an existing custom field with the provided DTO data.
     *
     * @param id the custom field ID
     * @param dto the DTO containing updated custom field data
     * @return the updated custom field as a RecoveryCustomFieldDto
     * @throws ResponseStatusException with status 404 if custom field is not found
     * @throws ResponseStatusException with status 409 if fieldName already exists
     */
    public RecoveryCustomFieldDto update(Long id, CreateCustomFieldDto dto) {
        CustomField existing = repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Custom field not found")
        );

        // Check if new fieldName already exists (ignoring current entity)
        if (!existing.getFieldName().equalsIgnoreCase(dto.fieldName()) && 
            repository.existsByFieldNameIgnoreCase(dto.fieldName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "A custom field with name '" + dto.fieldName() + "' already exists");
        }

        mapper.updateEntityFromDto(dto, existing);
        CustomField saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    /**
     * Deletes a custom field by its ID.
     *
     * @param id the custom field ID
     * @throws ResponseStatusException with status 404 if custom field is not found
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Custom field not found");
        }
        repository.deleteById(id);
    }
}
