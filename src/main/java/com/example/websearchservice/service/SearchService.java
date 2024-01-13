package com.example.websearchservice.service;

import com.example.websearchservice.client.ShopMockServiceClient;
import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.mapper.ProductAdapter;
import com.example.websearchservice.model.AdvancedSearchRequestBody;
import com.example.websearchservice.model.Category;
import com.example.websearchservice.model.Product;
import com.example.websearchservice.model.Review;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SearchService {

    private final ShopMockServiceClient shopMockServiceClient;
    private final ProductAdapter productAdapter;

    public CompletableFuture<Stream<Product>> getAllProductsIncludingExternalOnes() {
        CompletableFuture<Stream<ProductDTO>> allProducts = CompletableFuture.supplyAsync(
                () -> shopMockServiceClient.getAllProducts().stream());

        CompletableFuture<Stream<ProductDTO>> gameProducts = CompletableFuture.supplyAsync(
                () -> shopMockServiceClient.getAllGameProducts().stream());

        CompletableFuture<Stream<ProductDTO>> hardwareProducts = CompletableFuture.supplyAsync(
                () -> shopMockServiceClient.getAllHardwareProducts().stream());

        CompletableFuture<Stream<ProductDTO>> softwareToolProducts = CompletableFuture.supplyAsync(
                () -> shopMockServiceClient.getAllSoftwareToolProducts().stream());

        return CompletableFuture.allOf(allProducts, gameProducts, hardwareProducts, softwareToolProducts)
                .thenApply(v -> Stream.of(allProducts, gameProducts, hardwareProducts, softwareToolProducts)
                        .flatMap(CompletableFuture::join)
                        .distinct()
                        .map(productAdapter::adaptToEntity));
    }

    public List<Product> getAllProducts() {
        return shopMockServiceClient.getAllProducts()
                .stream()
                .map(productAdapter::adaptToEntity)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategory(final List<Category> categories) {
        return shopMockServiceClient.getAllProducts()
                .stream()
                .map(productAdapter::adaptToEntity)
                .filter(product -> new HashSet<>(product.getCategories()).containsAll(categories))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByPrice(final BigDecimal price) {
        return shopMockServiceClient.getAllProducts()
                .stream()
                .map(productAdapter::adaptToEntity)
                .filter(product -> product.getPrice().compareTo(price) <= 0)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByManufacturer(final String manufacturer) {
        return shopMockServiceClient.getAllProducts()
                .stream()
                .map(productAdapter::adaptToEntity)
                .filter(product -> manufacturer.equals(product.getManufacturer().getName()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByReviews(final Double avgReview) {
        return shopMockServiceClient.getAllProducts()
                .stream()
                .map(productAdapter::adaptToEntity)
                .filter(product -> getAverageRate(product.getReviews()) >= avgReview)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsAdvancedSearch(final AdvancedSearchRequestBody body) {
        return shopMockServiceClient.getAllProducts()
                .stream()
                .map(productAdapter::adaptToEntity)
                .filter(product -> optionallyFilterCategory(product, body) &&
                        optionallyFilterPrice(product, body) &&
                        optionallyFilterManufacturer(product, body) &&
                        optionallyFilterAvgReviews(product, body)
                )
                .collect(Collectors.toList());
    }

    private Double getAverageRate(final List<Review> reviews) {
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    private boolean optionallyFilterCategory(final Product product, final AdvancedSearchRequestBody body) {
        return body.categories().isEmpty() || new HashSet<>(product.getCategories()).containsAll(body.categories());
    }

    private boolean optionallyFilterPrice(final Product product, final AdvancedSearchRequestBody body) {
        return body.price() == null || product.getPrice().compareTo(body.price()) <= 0;
    }

    private boolean optionallyFilterManufacturer(final Product product, final AdvancedSearchRequestBody body) {
        return body.manufacturerName() == null || body.manufacturerName().equals(product.getManufacturer().getName());
    }

    private boolean optionallyFilterAvgReviews(final Product product, final AdvancedSearchRequestBody body) {
        return body.reviewRate() == null || getAverageRate(product.getReviews()) >= body.reviewRate();
    }
}
