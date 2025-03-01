package com.example.senior_project.dto;

import com.example.senior_project.model.NotificationType;
import com.example.senior_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private User user;
    private String title;
    private NotificationType type;
    private String message;
    private String link;
} 