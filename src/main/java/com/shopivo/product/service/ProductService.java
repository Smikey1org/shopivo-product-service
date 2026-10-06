package com.shopivo.product.service;

import com.shopivo.product.dto.ProductRequest;
import com.shopivo.product.dto.ProductResponse;
import com.shopivo.product.entity.Product;
import com.shopivo.product.exception.ResourceNotFoundException;
import com.shopivo.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
    }

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        apply(product, request);
        return toResponse(repository.save(product));
    }

    public ProductResponse update(UUID id, ProductRequest request) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        apply(product, request);
        return toResponse(repository.save(product));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }

        repository.deleteById(id);
    }

    private void apply(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setSlug(request.slug());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setImageUrl(request.imageUrl());
        product.setActive(request.active() == null || request.active());
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
            p.getId(), p.getName(), p.getSlug(), p.getDescription(),
            p.getPrice(), p.getStock(), p.getImageUrl(), p.getActive(),
            p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
