package com.departmental.store.api.product.service;

import com.departmental.store.api.product.controller.request.ProductCreateRequest;
import com.departmental.store.api.product.controller.request.ProductUpdateRequest;
import com.departmental.store.api.product.controller.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private Map<String, ProductCreateRequest> memoryCache;

    @Autowired
    public ProductsService() {
        this.memoryCache = new ConcurrentHashMap<>();
    }

    public void create(ProductCreateRequest request) {
        memoryCache.put(request.getName(), request);
    }

    public ProductResponse get(String id) {
        ProductCreateRequest input = memoryCache.get(id);
        return new ProductResponse(input.getName(), input.getPrice(), input.getQuantity());
    }

    public List<ProductResponse> allProducts() {
        return memoryCache.values()
                .stream()
                .map(input -> new ProductResponse(input.getName(), input.getPrice(), input.getQuantity()))
                .collect(Collectors.toList());
    }

    public void update(String id, ProductUpdateRequest request) {
        memoryCache.get(id).setPrice(request.getPrice());
    }

    public void delete(String id) {
        memoryCache.remove(id);
    }

}
