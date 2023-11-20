package com.example.websearchservice;

import com.example.websearchservice.dto.CategoryDTO;
import com.example.websearchservice.dto.ManufacturerDTO;
import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.dto.ReviewDTO;
import com.example.websearchservice.model.Category;
import com.example.websearchservice.model.Manufacturer;
import com.example.websearchservice.model.Product;
import com.example.websearchservice.model.Review;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProductDataProvider {

    public static ProductDTO getSimpleProductDTO() {
        return ProductDTO.builder()
                .id(UUID.fromString("f70ca4ca-f88a-4316-8cf1-25313931b2ec"))
                .name("Product 1")
                .description("description 1")
                .price(BigDecimal.valueOf(10.0))
                .manufacturer(ManufacturerDTO.builder()
                        .id(UUID.fromString("4fb221e8-0c1f-4e6c-9624-706853fbb4af"))
                        .name("Manufacturer 1")
                        .address("Address 1")
                        .contact("test@address.com")
                        .build())
                .categories(List.of(
                        CategoryDTO.BABY_PRODUCTS,
                        CategoryDTO.AUTOMOTIVE
                ))
                .createdAt(LocalDateTime.of(2023, 10, 19, 19, 0))
                .updatedAt(LocalDateTime.of(2023, 10, 19, 19, 0))
                .reviews(List.of(
                        ReviewDTO.builder()
                                .reviewerName("Reviewer 1")
                                .comment("Comment 1")
                                .rating(5)
                                .reviewDate(LocalDateTime.of(2023, 10, 19, 19, 0))
                                .build()
                ))
                .build();
    }

    public static Product getSimpleProduct() {
        return Product.builder()
                .id(UUID.fromString("f70ca4ca-f88a-4316-8cf1-25313931b2ec"))
                .name("Product 1")
                .description("description 1")
                .price(BigDecimal.valueOf(10.0))
                .manufacturer(Manufacturer.builder()
                        .id(UUID.fromString("4fb221e8-0c1f-4e6c-9624-706853fbb4af"))
                        .name("Manufacturer 1")
                        .address("Address 1")
                        .contact("test@address.com")
                        .build())
                .categories(List.of(
                        Category.BABY_PRODUCTS,
                        Category.AUTOMOTIVE
                ))
                .createdAt(LocalDateTime.of(2023, 10, 19, 19, 0))
                .updatedAt(LocalDateTime.of(2023, 10, 19, 19, 0))
                .reviews(List.of(
                        Review.builder()
                                .reviewerName("Reviewer 1")
                                .comment("Comment 1")
                                .rating(5)
                                .reviewDate(LocalDateTime.of(2023, 10, 19, 19, 0))
                                .build()
                ))
                .build();
    }
}
