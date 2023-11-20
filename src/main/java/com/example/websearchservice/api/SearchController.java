package com.example.websearchservice.api;

import com.example.websearchservice.model.AdvancedSearchRequestBody;
import com.example.websearchservice.model.Category;
import com.example.websearchservice.model.Product;
import com.example.websearchservice.service.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/search/products")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

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
