package com.example.websearchservice.mapper;

import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product dtoToProduct(ProductDTO productDTO);
}
