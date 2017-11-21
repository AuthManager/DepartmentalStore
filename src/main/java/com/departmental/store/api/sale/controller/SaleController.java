package com.departmental.store.api.sale.controller;


import com.departmental.store.api.product.controller.response.ProductResponse;
import com.departmental.store.api.sale.controller.request.CartItemRequest;
import com.departmental.store.api.sale.controller.response.ProductSaleEntityResponse;
import com.departmental.store.api.sale.controller.response.SaleEntityResponse;
import com.departmental.store.api.sale.controller.response.SaleResponse;
import com.departmental.store.api.sale.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> overallSales(@RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(value = "date", required = false) String date) {
        List<ProductSaleEntityResponse> overallSale = saleService.overallSale(startDate, endDate, date);
        return new ResponseEntity<>(overallSale, HttpStatus.OK);
    }

    @RequestMapping(path = "/{productId}",method = RequestMethod.GET)
    public ResponseEntity<?> productSales(@PathVariable(value = "productId", required = false) String productId,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate,
                                    @RequestParam(value = "date", required = false) String date) {
        ProductSaleEntityResponse productSale = saleService.getProductSale(productId, startDate, endDate, date);
        return new ResponseEntity<>(productSale, HttpStatus.OK);
    }

}
