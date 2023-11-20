package com.example.websearchservice.mapper;

import com.example.websearchservice.ProductDataProvider;
import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

class ProductAdapterTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductAdapter systemUnderTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_map_productDTO_to_product() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(productMapper.dtoToProduct(productDTO)).thenReturn(product);

        Product result = systemUnderTest.adaptToEntity(productDTO);

        Assertions.assertEquals(product, result);
    }
}