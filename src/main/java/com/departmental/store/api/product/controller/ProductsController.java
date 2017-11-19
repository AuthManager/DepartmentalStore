package com.departmental.store.api.product.controller;

import com.departmental.store.api.product.controller.request.ProductCreateRequest;
import com.departmental.store.api.product.controller.request.ProductUpdateRequest;
import com.departmental.store.api.product.controller.response.ProductResponse;
import com.departmental.store.api.product.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductsController {

    private ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> password(@RequestBody ProductCreateRequest productCreateRequest) {
        productsService.create(productCreateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> all() {
        List<ProductResponse> products = productsService.allProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePrice(@PathVariable("id") String id, @RequestBody ProductUpdateRequest request) {
        productsService.update(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        ProductResponse productResponse = productsService.get(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        productsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
