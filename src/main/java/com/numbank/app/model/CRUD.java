package com.numbank.app.model;

import java.util.List;

public interface CRUD<T, ID> {
    T getById(ID id);

    List<T> findAll();

    List<T> saveAll(List<T> toSave);

    T save(T toSave);
}
