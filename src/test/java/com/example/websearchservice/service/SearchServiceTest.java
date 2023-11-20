package com.example.websearchservice.service;

import com.example.websearchservice.ProductDataProvider;
import com.example.websearchservice.client.ShopMockServiceClient;
import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.mapper.ProductAdapter;
import com.example.websearchservice.model.AdvancedSearchRequestBody;
import com.example.websearchservice.model.Category;
import com.example.websearchservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SearchServiceTest {

    @Mock
    private ShopMockServiceClient shopMockServiceClient;

    @Mock
    private ProductAdapter productAdapter;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_get_all_products() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    void should_return_empty_if_no_data_retrieved() {
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.emptyList());

        List<Product> result = searchService.getAllProducts();
        assertTrue(result.isEmpty());
    }

    @Test
    void should_return_error_if_all_products_return_error() {
        when(shopMockServiceClient.getAllProducts()).thenThrow(new RuntimeException("Some error"));

        Exception exception = assertThrows(RuntimeException.class, () -> searchService.getAllProducts());
        assertEquals("Some error", exception.getMessage());
    }

    @Test
    void should_return_error_if_mapping_returned_error() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenThrow(new RuntimeException("Some error"));

        Exception exception = assertThrows(RuntimeException.class, () -> searchService.getAllProducts());
        assertEquals("Some error", exception.getMessage());
    }

    @Test
    void should_get_all_products_by_category() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByCategory(List.of(Category.BABY_PRODUCTS));
        assertFalse(result.isEmpty());
        assertEquals(product, result.get(0));
    }

    @Test
    void should_return_empty_if_category_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByCategory(List.of(Category.ARTS_CRAFTS));
        assertTrue(result.isEmpty());
    }

    @Test
    void should_get_all_products_by_price() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByPrice(BigDecimal.valueOf(11));
        assertFalse(result.isEmpty());
        assertEquals(product, result.get(0));
    }

    @Test
    void should_return_empty_if_price_is_higher() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByPrice(BigDecimal.ONE);
        assertTrue(result.isEmpty());
    }

    @Test
    void should_get_all_products_by_manufacturer() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByManufacturer("Manufacturer 1");
        assertFalse(result.isEmpty());
        assertEquals(product, result.get(0));
    }

    @Test
    void should_return_empty_if_manufacturer_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByManufacturer("Manufacturer 2");
        assertTrue(result.isEmpty());
    }

    @Test
    void should_get_all_products_by_reviews() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByReviews(4.0);
        assertFalse(result.isEmpty());
        assertEquals(product, result.get(0));
    }

    @Test
    void should_return_empty_if_review_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsByReviews(6.0);
        assertTrue(result.isEmpty());
    }

    @Test
    void should_get_all_products_by_advanced_details() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsAdvancedSearch(
                new AdvancedSearchRequestBody("Manufacturer 1", BigDecimal.valueOf(11), List.of(Category.BABY_PRODUCTS), 4.0));
        assertFalse(result.isEmpty());
        assertEquals(product, result.get(0));
    }

    @Test
    void should_return_empty_if_any_of_advanced_search_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();
        when(shopMockServiceClient.getAllProducts()).thenReturn(Collections.singletonList(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        List<Product> result = searchService.getProductsAdvancedSearch(
                new AdvancedSearchRequestBody("Manufacturer 2", BigDecimal.ONE, List.of(Category.BABY_PRODUCTS), 4.0));
        assertTrue(result.isEmpty());
    }
}
