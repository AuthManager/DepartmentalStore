package com.departmental.store.api.sale.repository.entity;

public class Item {

    private String id;
    private String name;
    private float unitPrice;
    private int soldQuantity;

    public Item(String id, String name, float unitPrice, int soldQuantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.soldQuantity = soldQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

}
