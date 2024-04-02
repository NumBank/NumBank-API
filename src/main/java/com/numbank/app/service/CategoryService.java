package com.numbank.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Category;
import com.numbank.app.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository repo;

    public Category getById(String id) {
        return repo.getById(id);
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category save(Category category) {
        List<Category> allCategories = repo.findAll();
        for (Category category2 : allCategories) {
            if (category.equals(category2)) {
                System.out.println("Category already exists");
                return null;
            }       
        }
        return repo.save(category);
    }

    public List<Category> saveAll(List<Category> categories) {
        return repo.saveAll(categories);
    }
}
