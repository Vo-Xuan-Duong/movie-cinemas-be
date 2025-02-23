package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.DiscountRequest;
import com.example.movie_cinemas_be.dtos.response.DiscountResponse;
import com.example.movie_cinemas_be.service.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {

    private DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public ResponseCustom<DiscountResponse> createDiscount(@RequestBody DiscountRequest discountRequest) {
        return ResponseCustom.<DiscountResponse>builder()
                .message("Successfully created discount")
                .data(discountService.createDiscount(discountRequest))
                .build();
    }

    @GetMapping
    public ResponseCustom<List<DiscountResponse>> getAllDiscounts() {
        return ResponseCustom.<List<DiscountResponse>>builder()
                .message("Successfully retrieved all discounts")
                .data(discountService.getAllDiscounts())
                .build();
    }

    @GetMapping("/{code}")
    public ResponseCustom<DiscountResponse> getDiscountById(@PathVariable String code) {
        return ResponseCustom.<DiscountResponse>builder()
                .message("Successfully retrieved discount")
                .data(discountService.getDiscountByCode(code))
                .build();
    }

    @PutMapping("/{discount_id}")
    public ResponseCustom<DiscountResponse> updateDiscount(@PathVariable long discount_id, @RequestBody DiscountRequest discountRequest) {
        return ResponseCustom.<DiscountResponse>builder()
                .message("Successfully updated discount")
                .data(discountService.updateDiscount(discount_id, discountRequest))
                .build();
    }

    @DeleteMapping("/{discount_id}")
    public ResponseCustom<Void> deleteDiscount(@PathVariable long discount_id) {
        return ResponseCustom.<Void>builder()
                .message("Successfully deleted discount")
                .build();
    }
}
