package com.numbank.app.model.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private String id;
    private String name;
    private String type;

    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
