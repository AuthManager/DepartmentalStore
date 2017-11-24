package com.departmental.store.api.sale.service;

import com.departmental.store.api.product.controller.response.ProductResponse;
import com.departmental.store.api.product.repository.ProductRepository;
import com.departmental.store.api.product.repository.entity.Product;
import com.departmental.store.api.sale.controller.request.CartItemRequest;
import com.departmental.store.api.sale.controller.response.CartItemResponse;
import com.departmental.store.api.sale.controller.response.ProductSaleEntityResponse;
import com.departmental.store.api.sale.controller.response.SaleEntityResponse;
import com.departmental.store.api.sale.controller.response.SaleResponse;
import com.departmental.store.api.sale.repository.ItemRepository;
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
    private ItemRepository itemRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, ItemRepository itemRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    public SaleResponse create(List<CartItemRequest> cartItems) {
        Sale sale = createSale(cartItems);
        return createSaleResponse(sale);
    }

    private Sale createSale(List<CartItemRequest> cartItems) {
        Sale sale = new Sale(LocalDate.now(), 0);
        saleRepository.save(sale);
        float totalPrice = 0;
        List<Item> items = new ArrayList<>();
        for (CartItemRequest cartItem : cartItems) {
            Product product = getProduct(cartItem.getProductId());
            items.add(new Item(sale, product, cartItem.getSellQuantity()));
            totalPrice += (product.getPrice() * cartItem.getSellQuantity());
            updateQuantity(product, cartItem.getSellQuantity());
        }
        itemRepository.save(items);
        sale.setTotalPrice(totalPrice);
        saleRepository.save(sale);
        return sale;
    }

    private SaleResponse createSaleResponse(Sale sale) {
        List<Item> items = itemRepository.findAllBySale(sale);
        List<CartItemResponse> itemsResponse = items.stream()
                .map(item -> new CartItemResponse(getProduct(item.getProduct().getId().toString()), item.getSoldQuantity()))
                .collect(Collectors.toList());

        return new SaleResponse(sale.getId().toString(), sale.getDate().toString(), sale.getTotalPrice(), itemsResponse);
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
        Iterable<Sale> sales = saleRepository.findAll();
        SaleEntityResponse saleEntityResponse = getSaleEntityResponse(product, sales, startDate, endDate, date);
        return new ProductSaleEntityResponse(ProductResponse.from(product), saleEntityResponse);
    }

    public List<ProductSaleEntityResponse> overallSale(String startDate, String endDate, String date) {
        Iterable<Product> products = productRepository.findAll();
        Iterable<Sale> sales = saleRepository.findAll();
        List<ProductSaleEntityResponse> overallSale = new LinkedList<>();
        for (Product product : products) {
            SaleEntityResponse saleEntityResponse = getSaleEntityResponse(product, sales, startDate, endDate, date);
            overallSale.add(new ProductSaleEntityResponse(ProductResponse.from(product), saleEntityResponse));
        }
        return overallSale;
    }

    private SaleEntityResponse getSaleEntityResponse(Product product,
                                                     Iterable<Sale> sales,
                                                     String startDate,
                                                     String endDate,
                                                     String date) {
        int soldQuantity = 0;
        float totalPrice = 0;
        for (Sale sale : sales) {
            if (inRange(sale.getDate(), startDate, endDate, date)) {
                List<Item> items = itemRepository.findAllBySale(sale);
                for (Item item : items) {
                    if (isProduct(product, item)) {
                        soldQuantity += item.getSoldQuantity();
                        totalPrice += (getProduct(item.getProduct().getId().toString()).getPrice() * item.getSoldQuantity());
                    }
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
