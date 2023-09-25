package com.example.nearby.controller;

import com.example.nearby.datamodel.domain.PriceHistory;
import com.example.nearby.datamodel.domain.Product;
import com.example.nearby.service.PriceHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pricehistory")
@CrossOrigin("http://localhost:3000")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<List<PriceHistory>> getProductsByName(@PathVariable Long productId) {
        List<PriceHistory> priceHistoryList = priceHistoryService.getPriceHistoryForProduct(productId);
        return ResponseEntity.ok(priceHistoryList);
    }
}
