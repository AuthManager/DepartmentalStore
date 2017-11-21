package com.departmental.store.api.sale.repository;

import com.departmental.store.api.sale.repository.entity.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SaleRepository extends MongoRepository<Sale, String> {

}
