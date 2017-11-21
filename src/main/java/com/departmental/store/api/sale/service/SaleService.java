package com.departmental.store.api.sale.service;

import com.departmental.store.api.product.repository.ProductRepository;
import com.departmental.store.api.product.repository.entity.Product;
import com.departmental.store.api.sale.controller.request.CartItemRequest;
import com.departmental.store.api.sale.controller.response.CartItemResponse;
import com.departmental.store.api.sale.controller.response.SaleResponse;
import com.departmental.store.api.sale.repository.SaleRepository;
import com.departmental.store.api.sale.repository.entity.Item;
import com.departmental.store.api.sale.repository.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public SaleResponse create(List<CartItemRequest> cartItems) {
        Sale sale = createSale(cartItems);
        return createSaleResponse(sale);
    }

    private Sale createSale(List<CartItemRequest> cartItems) {
        List<Item> items = new ArrayList<>();
        float totalPrice = 0;
        for (CartItemRequest cartItem : cartItems) {
            Product product = getProduct(cartItem.getProductId());
            items.add(new Item(product.getId(), product.getName(), product.getPrice(), cartItem.getSellQuantity()));
            totalPrice += (product.getPrice() * cartItem.getSellQuantity());
            updateQuantity(product, cartItem.getSellQuantity());
        }
        Sale sale = new Sale(LocalDate.now(), items, totalPrice);
        saleRepository.insert(sale);
        return sale;
    }

    private SaleResponse createSaleResponse(Sale sale) {
        List<CartItemResponse> itemsResponse = sale.getItems()
                .stream()
                .map(item -> new CartItemResponse(item.getId(), item.getName(), item.getUnitPrice(), item.getSoldQuantity()))
                .collect(Collectors.toList());

        return new SaleResponse(sale.getId(), sale.getDate().toString(), sale.getTotalPrice(), itemsResponse);
    }

    private void updateQuantity(Product product, int soldQuantity) {
        product.setQuantity(product.getQuantity() - soldQuantity);
        productRepository.save(product);
    }

    private Product getProduct(String productId) {
        return productRepository.findOne(productId);
    }

}
