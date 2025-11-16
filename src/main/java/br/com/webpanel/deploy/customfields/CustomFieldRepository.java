package br.com.webpanel.deploy.customfields;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for CustomField entity.
 */
public interface CustomFieldRepository extends JpaRepository<CustomField, Long> {
    boolean existsByFieldNameIgnoreCase(String fieldName);
}
