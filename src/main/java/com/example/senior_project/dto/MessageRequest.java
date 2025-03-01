package com.example.senior_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageRequest {
    @NotNull
    private Long receiverId;
    
    @NotBlank
    @Size(min = 1, max = 1000)
    private String content;
    
    private Long offerId;
} 