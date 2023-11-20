package com.example.websearchservice.api;

import com.example.websearchservice.ProductDataProvider;
import com.example.websearchservice.model.Category;
import com.example.websearchservice.model.Product;
import com.example.websearchservice.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @MockBean
    private SearchService searchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_all_products() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/search/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_return_error_if_wrong_url() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/search/product"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void should_return_error_if_service_return_error() throws Exception {
        when(searchService.getAllProducts()).thenThrow(new RestClientException("Some error"));

        mockMvc.perform(get("/search/products"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void should_return_all_products_by_category() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByCategory(List.of(Category.BABY_PRODUCTS))).thenReturn(List.of(product));

        mockMvc.perform(get("/search/products/categories")
                        .param("categories", "BABY_PRODUCTS"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void should_return_bad_request_for_wrong_enum() throws Exception {
        mockMvc.perform(get("/search/products/categories")
                        .param("categories", "BABY_PROD"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_bad_request_for_wrong_query_param() throws Exception {
        mockMvc.perform(get("/search/products/categories")
                        .param("category", "BABY_PRODUCTS"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_all_products_by_price() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByPrice(BigDecimal.valueOf(11))).thenReturn(List.of(product));

        mockMvc.perform(get("/search/products/price")
                        .param("price", "11"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void should_return_bad_request_for_wrong_query_type() throws Exception {
        mockMvc.perform(get("/search/products/price")
                        .param("price", "11test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_bad_request_for_wrong_price_query_param() throws Exception {
        mockMvc.perform(get("/search/products/price")
                        .param("prize", "11"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_all_products_by_manufacturer() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByManufacturer("Manufacturer 1")).thenReturn(List.of(product));

        mockMvc.perform(get("/search/products/manufacturer")
                        .param("manufacturer", "Manufacturer 1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void should_return_bad_request_if_wrong_manufacturer_query_param() throws Exception {
        mockMvc.perform(get("/search/products/manufacturer")
                        .param("fact", "Manufacturer 1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_all_products_by_review() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByReviews(4.0)).thenReturn(List.of(product));

        mockMvc.perform(get("/search/products/reviews")
                        .param("avgReview", "4.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_return_bad_request_if_wrong_review_data_type() throws Exception {
        mockMvc.perform(get("/search/products/reviews")
                        .param("avgReview", "4.0test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_bad_request_if_wrong_review_query_param_name() throws Exception {
        mockMvc.perform(get("/search/products/reviews")
                        .param("review", "4.0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_all_products_by_advanced_search() throws Exception {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsAdvancedSearch(any())).thenReturn(List.of(product));

        mockMvc.perform(post("/search/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"manufacturerName\":\"Manufacturer 1\",\"price\":11,\"categories\":[\"BABY_PRODUCTS\"],\"reviewRate\":4.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_return_bad_request_if_wrong_body_provided() throws Exception {
        mockMvc.perform(post("/search/products"))
                .andExpect(status().isBadRequest());
    }

}
