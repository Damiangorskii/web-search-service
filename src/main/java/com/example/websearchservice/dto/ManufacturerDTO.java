package com.example.websearchservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDTO {

    @NotNull
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String contact;
}
