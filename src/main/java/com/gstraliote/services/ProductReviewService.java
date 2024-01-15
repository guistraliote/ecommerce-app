package com.gstraliote.services;

import com.gstraliote.dto.ProductReviewDTO;
import com.gstraliote.entities.Product;
import com.gstraliote.entities.ProductReview;
import com.gstraliote.repositories.ProductRepository;
import com.gstraliote.repositories.ProductReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;

    public ProductReviewService(ProductReviewRepository productReviewRepository, ProductRepository productRepository) {
        this.productReviewRepository = productReviewRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductReviewDTO createProductReview(ProductReviewDTO productReviewDTO, Long productId) {
        ProductReview productReviewEntity = convertToEntity(productReviewDTO, productId);
        ProductReview savedProductReview = productReviewRepository.save(productReviewEntity);

        return convertToDTO(savedProductReview);
    }

    @Transactional(readOnly = true)
    public ProductReviewDTO getProductReviewById(Long reviewId) {
        return productReviewRepository.findById(reviewId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ProductReviewDTO> getProductReviewsByProductId(Long productId) {
        List<ProductReview> productReviews = productReviewRepository.findByProductId(productId);
        return productReviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductReviewDTO updateProductReview(Long reviewId, ProductReviewDTO updatedProductReviewDTO) {
        return productReviewRepository.findById(reviewId)
                .map(existingProductReview -> {
                    updateProductReviewEntity(existingProductReview, updatedProductReviewDTO);
                    ProductReview updatedProductReview = productReviewRepository.save(existingProductReview);
                    return convertToDTO(updatedProductReview);
                })
                .orElseThrow(() -> new IllegalArgumentException("Avaliação de produto não encontrada com o ID: " + reviewId));
    }

    @Transactional
    public void deleteProductReview(Long reviewId) {
        productReviewRepository.deleteById(reviewId);
    }

    @Transactional(readOnly = true)
    public Page<ProductReviewDTO> findAllProductReviewsPaged(Pageable pageable) {
        Page<ProductReview> productReviewPage = productReviewRepository.findAll(pageable);

        return productReviewPage.map(this::convertToDTO);
    }

    private ProductReview convertToEntity(ProductReviewDTO productReviewDTO, Long productId) {
        ProductReview productReviewEntity = new ProductReview();
        productReviewEntity.setReview(productReviewDTO.review());
        productReviewEntity.setReviewRate(productReviewDTO.reviewRate());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + productId));
        productReviewEntity.setProduct(product);

        return productReviewEntity;
    }

    private ProductReviewDTO convertToDTO(ProductReview productReviewEntity) {
        return new ProductReviewDTO(
                productReviewEntity.getId(),
                productReviewEntity.getReview(),
                productReviewEntity.getPostDate(),
                productReviewEntity.getReviewRate(),
                productReviewEntity.getProduct().getId()
        );
    }

    private void updateProductReviewEntity(ProductReview existingProductReview, ProductReviewDTO updatedProductReviewDTO) {
        existingProductReview.setReview(updatedProductReviewDTO.review());
        existingProductReview.setReviewRate(updatedProductReviewDTO.reviewRate());
    }
}