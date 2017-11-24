package com.departmental.store.api.product.service;

import com.departmental.store.api.product.controller.request.ProductCreateRequest;
import com.departmental.store.api.product.controller.request.ProductUpdateRequest;
import com.departmental.store.api.product.controller.response.ProductResponse;
import com.departmental.store.api.product.repository.ProductRepository;
import com.departmental.store.api.product.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private ProductRepository productRepository;

    @Autowired
    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(ProductCreateRequest request) {
        Product entity = new Product();
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuantity());
        productRepository.save(entity);
    }

    public ProductResponse get(String id) {
        Product product = productRepository.findOne(new Integer(id));
        return ProductResponse.from(product);
    }

    public List<ProductResponse> allProducts() {
        Iterable<Product> products = productRepository.findAll();
        List<ProductResponse> result = new ArrayList<>();
        products.forEach(input -> result.add(ProductResponse.from(input)));
        return result;
    }

    public void update(String id, ProductUpdateRequest request) {
        Product product = productRepository.findOne(new Integer(id));
        product.setPrice(request.getPrice());
        productRepository.save(product);
    }

    public void delete(String id) {
        productRepository.delete(new Integer(id));
    }

}
