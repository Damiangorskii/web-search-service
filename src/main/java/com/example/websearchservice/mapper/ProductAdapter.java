package com.example.websearchservice.mapper;

import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductAdapter {

    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    public Product adaptToEntity(ProductDTO productDTO) {
        return productMapper.dtoToProduct(productDTO);
    }
}
