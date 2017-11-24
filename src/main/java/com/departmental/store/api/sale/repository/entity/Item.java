package com.departmental.store.api.sale.repository.entity;

import com.departmental.store.api.product.repository.entity.Product;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER, optional = false)
    private Sale sale;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER, optional = false)
    private Product product;

    private int soldQuantity;

    public Item() {
    }

    public Item(Sale sale, Product product, int soldQuantity) {
        this.sale = sale;
        this.product = product;
        this.soldQuantity = soldQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
}
