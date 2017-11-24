package com.departmental.store.api.sale.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private LocalDate date;

    private double totalPrice;

    public Sale() {
    }

    public Sale(LocalDate date, double totalPrice) {
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
