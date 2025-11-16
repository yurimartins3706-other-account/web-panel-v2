package br.com.webpanel.deploy.customfields;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomField entity for storing custom form fields.
 * Supports various data types: string, varchar, text, integer, float, boolean, array.
 */
@Entity
@Table(name = "custom_fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "fieldName is required")
    private String fieldName;

    @Column(nullable = false)
    @NotBlank(message = "fieldLabel is required")
    private String fieldLabel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(nullable = false)
    private Boolean isRequired = false;

    private Integer maxLength;

    @ElementCollection
    private List<String> arrayOptions;

    private String defaultValue;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    /**
     * Enum for supported field types
     */
    public enum FieldType {
        STRING("String (Texto Curto)"),
        VARCHAR("Varchar (Texto Limitado)"),
        TEXT("Text (Texto Longo)"),
        INTEGER("Integer (Número Inteiro)"),
        FLOAT("Float (Número Decimal)"),
        BOOLEAN("Boolean (Verdadeiro/Falso)"),
        ARRAY("Array (Lista de Opções)");

        private final String description;

        FieldType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
