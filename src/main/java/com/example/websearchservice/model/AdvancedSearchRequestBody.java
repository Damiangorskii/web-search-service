package com.example.websearchservice.model;

import java.math.BigDecimal;
import java.util.List;

public record AdvancedSearchRequestBody(String manufacturerName, BigDecimal price, List<Category> categories,
                                        Double reviewRate) {

}
