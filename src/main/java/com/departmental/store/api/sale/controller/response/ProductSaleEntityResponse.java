package com.departmental.store.api.sale.controller.response;

import com.departmental.store.api.product.controller.response.ProductResponse;

public class ProductSaleEntityResponse extends SaleEntityResponse{

    private ProductResponse product;

    public ProductSaleEntityResponse(ProductResponse product,SaleEntityResponse saleEntityResponse) {
        super(saleEntityResponse.getSoldQuantity(), saleEntityResponse.getTotalPrice());
        this.product = product;
    }

    public ProductResponse getProduct() {
        return product;
    }

}
