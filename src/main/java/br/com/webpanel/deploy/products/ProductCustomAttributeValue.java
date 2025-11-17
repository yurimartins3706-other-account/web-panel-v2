package br.com.webpanel.deploy.products;

import br.com.webpanel.deploy.customfields.CustomField;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ProductCustomAttributeValue entity for storing custom attribute values for products.
 * Links a Product with a CustomField and stores the corresponding value.
 * This enables products to have N custom attributes (e.g., size, color, etc.)
 */
@Entity
@Table(name = "product_custom_attribute_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCustomAttributeValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_field_id", nullable = false)
    private CustomField customField;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String value;
}
