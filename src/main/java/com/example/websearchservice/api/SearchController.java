package com.example.websearchservice.api;

import com.example.websearchservice.model.AdvancedSearchRequestBody;
import com.example.websearchservice.model.Category;
import com.example.websearchservice.model.Product;
import com.example.websearchservice.service.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/search/products")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/external")
    public CompletableFuture<Stream<Product>> getAllProductsIncludingExternalOnes() {
        return searchService.getAllProductsIncludingExternalOnes();
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return searchService.getAllProducts();
    }

    @GetMapping("/categories")
    public List<Product> getProductsByCategory(@RequestParam final List<Category> categories) {
        return searchService.getProductsByCategory(categories);
    }

    @GetMapping("/price")
    public List<Product> getProductsByPrice(@RequestParam final BigDecimal price) {
        return searchService.getProductsByPrice(price);
    }

    @GetMapping("/manufacturer")
    public List<Product> getProductsByManufacturer(@RequestParam final @NotBlank String manufacturer) {
        return searchService.getProductsByManufacturer(manufacturer);
    }

    @GetMapping("/reviews")
    public List<Product> getProductsByReviews(@RequestParam final Double avgReview) {
        return searchService.getProductsByReviews(avgReview);
    }

    @PostMapping
    public List<Product> getProductsAdvancedSearch(@RequestBody final @Valid AdvancedSearchRequestBody body) {
        return searchService.getProductsAdvancedSearch(body);
    }
}
