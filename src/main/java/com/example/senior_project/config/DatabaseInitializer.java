package com.example.senior_project.config;

import com.example.senior_project.model.Category;
import com.example.senior_project.model.User;
import com.example.senior_project.model.UserType;
import com.example.senior_project.repository.CategoryRepository;
import com.example.senior_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Admin kullanıcısı yoksa oluştur
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = User.builder()
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("User")
                    .phoneNumber("5551234567")
                    .userType(UserType.ADMIN)
                    .isActive(true)
                    .emailVerified(true)
                    .build();
            
            userRepository.save(admin);
        }

        // Ana kategorileri oluştur
        if (categoryRepository.count() == 0) {
            Category food = new Category();
            food.setName("Yiyecek");
            food.setDescription("El yapımı yiyecek ürünleri");
            categoryRepository.save(food);

            Category handicraft = new Category();
            handicraft.setName("El Sanatları");
            handicraft.setDescription("El yapımı sanat ürünleri");
            categoryRepository.save(handicraft);

            Category textile = new Category();
            textile.setName("Tekstil");
            textile.setDescription("El yapımı tekstil ürünleri");
            categoryRepository.save(textile);

            Category cosmetic = new Category();
            cosmetic.setName("Kozmetik");
            cosmetic.setDescription("Doğal kozmetik ürünleri");
            categoryRepository.save(cosmetic);

            // Alt kategoriler
            Category bakery = new Category();
            bakery.setName("Fırın Ürünleri");
            bakery.setDescription("El yapımı fırın ürünleri");
            bakery.setParent(food);
            categoryRepository.save(bakery);

            Category jewelry = new Category();
            jewelry.setName("Takı");
            jewelry.setDescription("El yapımı takı ürünleri");
            jewelry.setParent(handicraft);
            categoryRepository.save(jewelry);
        }
    }
} 