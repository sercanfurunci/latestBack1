package com.example.senior_project.service.seller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.example.senior_project.model.User;
import com.example.senior_project.repository.UserRepository;
import com.example.senior_project.dto.SellerProfileUpdateRequest;

@Service
@RequiredArgsConstructor
public class SellerProfileService {
    private final UserRepository userRepository;

    @Transactional
    public User updateProfile(SellerProfileUpdateRequest request, User seller) {
        seller.setShopDescription(request.getShopDescription());
        seller.setBiography(request.getBiography());
        seller.setPhoneNumber(request.getPhoneNumber());
        
        if (request.getProfilePicture() != null) {
            seller.setProfilePicture(request.getProfilePicture());
        }

        return userRepository.save(seller);
    }

    public User getProfile(Long sellerId) {
        return userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
    }
} 