package com.departmental.store.api.product.controller.response;

import com.departmental.store.api.product.repository.entity.Product;

public class ProductResponse {

    private String id;
    private String name;
    private float price;
    private int quantity;

    public ProductResponse(String id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductResponse that = (ProductResponse) o;

        if (Float.compare(that.price, price) != 0) return false;
        if (quantity != that.quantity) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + quantity;
        return result;
    }

    public static ProductResponse from(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }

}
