package com.departmental.store.api.sale.repository;

import com.departmental.store.api.sale.repository.entity.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SaleRepository extends CrudRepository<Sale, Integer> {

}
