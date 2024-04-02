package com.numbank.app.controller;

import com.numbank.app.model.entity.Category;
import com.numbank.app.service.CategoryService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {
    CategoryService service;

    @GetMapping({"", "/"})
    public List<Category> getAll() {
        return service.getAll();
    }

    @PostMapping({"", "/"})
    public Category save(@RequestBody Category category) {
        return service.save(category);
    }
}
