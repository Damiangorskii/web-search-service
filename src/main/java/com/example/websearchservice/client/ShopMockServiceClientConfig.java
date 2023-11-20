package com.example.websearchservice.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.api.shop-mock-service")
@Getter
@Setter
public class ShopMockServiceClientConfig {

    private String url;
}
