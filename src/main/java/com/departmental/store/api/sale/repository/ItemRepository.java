package com.departmental.store.api.sale.repository;

import com.departmental.store.api.sale.repository.entity.Item;
import com.departmental.store.api.sale.repository.entity.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findAllBySale(Sale sale);

}
