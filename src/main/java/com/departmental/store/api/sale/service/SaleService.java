package com.departmental.store.api.sale.service;

import com.departmental.store.api.product.controller.response.ProductResponse;
import com.departmental.store.api.product.repository.ProductRepository;
import com.departmental.store.api.product.repository.entity.Product;
import com.departmental.store.api.sale.controller.request.CartItemRequest;
import com.departmental.store.api.sale.controller.response.CartItemResponse;
import com.departmental.store.api.sale.controller.response.ProductSaleEntityResponse;
import com.departmental.store.api.sale.controller.response.SaleEntityResponse;
import com.departmental.store.api.sale.controller.response.SaleResponse;
import com.departmental.store.api.sale.repository.SaleRepository;
import com.departmental.store.api.sale.repository.entity.Item;
import com.departmental.store.api.sale.repository.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            items.add(new Item(product.getId().toString(), product.getName(), product.getPrice(), cartItem.getSellQuantity()));
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
        return productRepository.findOne(new Integer(productId));
    }

    public ProductSaleEntityResponse getProductSale(String productId, String startDate, String endDate, String date) {
        Product product = productRepository.findOne(new Integer(productId));
        List<Sale> sales = saleRepository.findAll();
        SaleEntityResponse saleEntityResponse = getSaleEntityResponse(product, sales, startDate, endDate, date);
        return new ProductSaleEntityResponse(ProductResponse.from(product), saleEntityResponse);
    }

    public List<ProductSaleEntityResponse> overallSale(String startDate, String endDate, String date) {
        Iterable<Product> products = productRepository.findAll();
        List<Sale> sales = saleRepository.findAll();
        List<ProductSaleEntityResponse> overallSale = new LinkedList<>();
        for (Product product : products) {
            SaleEntityResponse saleEntityResponse = getSaleEntityResponse(product, sales, startDate, endDate, date);
            overallSale.add(new ProductSaleEntityResponse(ProductResponse.from(product), saleEntityResponse));
        }
        return overallSale;
    }

    private SaleEntityResponse getSaleEntityResponse(Product product,
                                                     List<Sale> sales,
                                                     String startDate,
                                                     String endDate,
                                                     String date) {
        int soldQuantity = 0;
        float totalPrice = 0;
        for (Sale sale : sales) {
            if (inRange(sale.getDate(), startDate, endDate, date))
                for (Item item : sale.getItems()) {
                    if (isProduct(product, item)) {
                        soldQuantity += item.getSoldQuantity();
                        totalPrice += (item.getUnitPrice() * item.getSoldQuantity());
                    }
                }
        }
        return new SaleEntityResponse(soldQuantity, totalPrice);
    }

    private boolean inRange(LocalDate saleDate, String startDate, String endDate, String date) {
        boolean isRange = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (date != null) {
            String actual = saleDate.format(formatter);
            isRange = actual.equals(date);
        } else if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter).plusDays(1);
            isRange = saleDate.isAfter(start) && saleDate.isBefore(end);
        }
        return isRange;
    }

    private boolean isProduct(Product product, Item item) {
        return item.getId().equals(product.getId());
    }

}
