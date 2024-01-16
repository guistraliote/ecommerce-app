package com.gstraliote.productReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-review")
public class ProductReviewAPI {

    private final ProductReviewService productReviewService;

    public ProductReviewAPI(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ProductReviewDTO> createProductReview(
            @RequestBody ProductReviewDTO productReviewDTO,
            @PathVariable Long productId) {
        ProductReviewDTO createdProductReview = productReviewService.createProductReview(productReviewDTO, productId);
        return new ResponseEntity<>(createdProductReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProductReviewDTO>> getAllProductReviewsPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "postDate") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
        Page<ProductReviewDTO> productReviewPage = productReviewService.findAllProductReviewsPaged(pageRequest);

        return ResponseEntity.ok(productReviewPage);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ProductReviewDTO> getProductReviewById(@PathVariable Long reviewId) {
        ProductReviewDTO productReviewDTO = productReviewService.getProductReviewById(reviewId);
        return ResponseEntity.ok(productReviewDTO);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductReviewDTO>> getProductReviewsByProductId(@PathVariable Long productId) {
        List<ProductReviewDTO> productReviews = productReviewService.getProductReviewsByProductId(productId);
        return ResponseEntity.ok(productReviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ProductReviewDTO> updateProductReview(
            @PathVariable Long reviewId,
            @RequestBody ProductReviewDTO updatedProductReviewDTO) {
        ProductReviewDTO updatedProductReview = productReviewService.updateProductReview(reviewId, updatedProductReviewDTO);
        return ResponseEntity.ok(updatedProductReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long reviewId) {
        productReviewService.deleteProductReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}