package com.departmental.store.api.sale.controller;


import com.departmental.store.api.sale.controller.request.CartItemRequest;
import com.departmental.store.api.sale.controller.response.SaleResponse;
import com.departmental.store.api.sale.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody List<CartItemRequest> cartItems) {
        SaleResponse saleResponse = saleService.create(cartItems);
        return new ResponseEntity<>(saleResponse, HttpStatus.OK);
    }


}
