package com.departmental.store.api.product.repository;

import com.departmental.store.api.product.repository.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepository extends MongoRepository<Product, String> {


}
