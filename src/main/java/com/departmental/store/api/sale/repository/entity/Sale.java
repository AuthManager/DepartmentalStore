package com.departmental.store.api.sale.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
public class Sale {

    @Id
    private String id;
    private LocalDate date;
    private List<Item> items;
    private double totalPrice;

    @PersistenceConstructor
    public Sale(String id, LocalDate date, List<Item> items, double totalPrice) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Sale(LocalDate date, List<Item> items, double totalPrice) {
        this.date = date;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
