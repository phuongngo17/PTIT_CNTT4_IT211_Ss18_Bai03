package org.example.bai03.service.impl;

import lombok.RequiredArgsConstructor;

import org.example.bai03.model.entity.Product;
import org.example.bai03.reponsitory.ProductRepository;
import org.example.bai03.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl
        implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {

        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {

        return productRepository.save(product);
    }

    @Override
    public Product update(Long id,
                          Product product) {

        Product oldProduct =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Không tìm thấy sản phẩm"));

        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setSize(product.getSize());
        oldProduct.setToppings(product.getToppings());

        return productRepository.save(oldProduct);
    }

    @Override
    public void delete(Long id) {

        productRepository.deleteById(id);
    }
}

