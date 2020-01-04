package com.suslov.service;

import com.suslov.controllers.repr.ProductRepr;
import com.suslov.persist.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductRepr> findAll();

    ProductRepr findById(Long id);

    void deleteById(Long id);

    void save(ProductRepr product) throws IOException;

    ProductRepr findByName(String name);
}
