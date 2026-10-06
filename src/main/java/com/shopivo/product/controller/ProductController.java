package com.shopivo.product.controller;

import com.shopivo.product.dto.ProductRequest;
import com.shopivo.product.dto.ProductResponse;
import com.shopivo.product.service.ProductService;
import com.shopivo.product.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> findAll() {
        return new ApiResponse<>(
                true,
                "All Product fetched successfully",
                service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> findById(@PathVariable UUID id) {
        return new ApiResponse<>(
                true,
                "Product fetched successfully",
                service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return new ApiResponse<>(
                true,
                "Product created successfully",
                service.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request) {
        return new ApiResponse<>(
                true,
                "Product updated successfully",
                service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return new ApiResponse<>(
                true,
                "Product deleted successfully",
                null);
    }

}
