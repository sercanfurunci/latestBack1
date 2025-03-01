package com.example.senior_project.model;

import lombok.Getter;

@Getter
public enum OfferStatus {
    PENDING("Beklemede"),
    ACCEPTED("Kabul Edildi"),
    REJECTED("Reddedildi"),
    CANCELLED("İptal Edildi");

    private final String displayName;

    OfferStatus(String displayName) {
        this.displayName = displayName;
    }
} 