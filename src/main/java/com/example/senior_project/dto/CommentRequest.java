package com.example.senior_project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CommentRequest {
    @NotNull
    private Long productId;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String content;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
} 