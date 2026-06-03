package org.example.bai03.service;

import org.example.bai03.model.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product);

    Product update(Long id, Product product);

    void delete(Long id);
}