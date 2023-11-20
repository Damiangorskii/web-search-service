package com.example.websearchservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    @NotBlank
    private String reviewerName;
    @NotBlank
    private String comment;
    @NotNull
    private int rating;
    private LocalDateTime reviewDate;
}
