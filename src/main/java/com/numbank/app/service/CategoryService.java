package com.numbank.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.numbank.app.model.entity.Category;
import com.numbank.app.repository.CategoryRepository;

@Service
public class CategoryService {
    private CategoryRepository repo;

    public Category getById(Integer id) {
        return repo.getById(id);
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public List<Category> saveAll(List<Category> categories) {
        return repo.saveAll(categories);
    }
}
