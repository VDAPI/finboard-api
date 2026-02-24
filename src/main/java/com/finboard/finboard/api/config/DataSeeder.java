package com.finboard.finboard.api.config;

import com.finboard.finboard.api.entity.Category;
import com.finboard.finboard.api.entity.CategoryType;
import com.finboard.finboard.api.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public DataSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            List<Category> categories = List.of(
                    new Category(null, "Jedzenie", "🍔", "#FF5733", CategoryType.EXPENSE),
                    new Category(null, "Transport", "🚗", "#33A1FF", CategoryType.EXPENSE),
                    new Category(null, "Rozrywka", "🎮", "#9B59B6", CategoryType.EXPENSE),
                    new Category(null, "Zdrowie", "💊", "#2ECC71", CategoryType.EXPENSE),
                    new Category(null, "Rachunki", "📄", "#E67E22", CategoryType.EXPENSE),
                    new Category(null, "Zakupy", "🛒", "#1ABC9C", CategoryType.EXPENSE),
                    new Category(null, "Edukacja", "📚", "#3498DB", CategoryType.EXPENSE),
                    new Category(null, "Wynagrodzenie", "💰", "#27AE60", CategoryType.INCOME),
                    new Category(null, "Freelance", "💻", "#8E44AD", CategoryType.INCOME),
                    new Category(null, "Inne", "📌", "#95A5A6", CategoryType.BOTH)
            );
            categoryRepository.saveAll(categories);
            System.out.println("Zaladowano: " + categories.size() + " kategorii");
        }
    }
}
