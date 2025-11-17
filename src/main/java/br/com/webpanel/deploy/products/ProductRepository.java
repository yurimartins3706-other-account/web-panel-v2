package br.com.webpanel.deploy.products;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Product entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Check if a product with the given SKU already exists (case-insensitive).
     *
     * @param sku the product SKU
     * @return true if exists, false otherwise
     */
    boolean existsBySkuIgnoreCase(String sku);
}
