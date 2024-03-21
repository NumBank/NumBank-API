package com.numbank.app.service;

import java.util.List;
import java.util.UUID;

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
            category.setId(UUID.randomUUID().toString());
        return repo.save(category);
    }

    public List<Category> saveAll(List<Category> categories) {
        for (Category category : categories) {
            category.setId(UUID.randomUUID().toString());
        }
        return repo.saveAll(categories);
    }
}
