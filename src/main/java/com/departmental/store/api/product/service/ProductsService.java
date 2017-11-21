package com.departmental.store.api.product.service;

import com.departmental.store.api.product.controller.request.ProductCreateRequest;
import com.departmental.store.api.product.controller.request.ProductUpdateRequest;
import com.departmental.store.api.product.controller.response.ProductResponse;
import com.departmental.store.api.product.repository.ProductRepository;
import com.departmental.store.api.product.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private ProductRepository productRepository;

    @Autowired
    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(ProductCreateRequest request) {
        productRepository.insert(new Product(request.getName(), request.getPrice(), request.getQuantity()));
    }

    public ProductResponse get(String id) {
        Product product = productRepository.findOne(id);
        return ProductResponse.from(product);
    }

    public List<ProductResponse> allProducts() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public void update(String id, ProductUpdateRequest request) {
        Product product = productRepository.findOne(id);
        product.setPrice(request.getPrice());
        productRepository.save(product);
    }

    public void delete(String id) {
        productRepository.delete(id);
    }

}
