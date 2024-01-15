package com.gstraliote.services;

import com.gstraliote.dto.ProductDTO;
import com.gstraliote.entities.Category;
import com.gstraliote.entities.Product;
import com.gstraliote.repositories.CategoryRepository;
import com.gstraliote.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productEntity = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(productEntity);
        return convertToDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllProductsPaged(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);

        return productPage.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO updatedProductDTO) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    updateProductEntity(existingProduct, updatedProductDTO);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return convertToDTO(updatedProduct);
                })
                .orElse(null);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product productEntity = new Product();
        productEntity.setName(productDTO.name());
        productEntity.setDescription(productDTO.description());
        productEntity.setPrice(productDTO.price());
        productEntity.setBrand(productDTO.brand());
        productEntity.setAddDate(productDTO.addDate());
        productEntity.setStockQuantity(productDTO.stockQuantity());
        productEntity.setUpdateDate(productDTO.updateDate());

        if (productDTO.categoryId() != null) {
            Category category = categoryRepository.findById(productDTO.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
            productEntity.setCategoryDetails(category);
        }

        return productEntity;
    }

    private ProductDTO convertToDTO(Product productEntity) {
        return new ProductDTO(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStockQuantity(),
                productEntity.getBrand(),
                productEntity.getWeight(),
                productEntity.getAddDate(),
                productEntity.getUpdateDate(),
                (productEntity.getCategory() != null) ? productEntity.getCategory().getId() : null,
                productEntity.getProductCategory(),
                productEntity.getProductCategoryPath()
        );
    }

    private void updateProductEntity(Product existingProduct, ProductDTO updatedProductDTO) {
        existingProduct.setName(updatedProductDTO.name());
        existingProduct.setDescription(updatedProductDTO.description());

        if (updatedProductDTO.categoryId() != null) {
            Category category = categoryRepository.findById(updatedProductDTO.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
            existingProduct.setCategoryDetails(category);
        }
    }
}