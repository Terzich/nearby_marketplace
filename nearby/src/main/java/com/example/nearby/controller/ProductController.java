package com.example.nearby.controller;

import com.example.nearby.datamodel.domain.Category;
import com.example.nearby.datamodel.domain.Product;
import com.example.nearby.datamodel.dto.CreateProductRequest;
import com.example.nearby.datamodel.dto.UpdateProductRequest;
import com.example.nearby.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin("http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Optional<Product> productOptional = productService.getById(productId);
        return productOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @RequestMapping(value = "/coordinates")
    public ResponseEntity<List<Product>> getProductsNearestToCoordinate(@RequestParam(name = "lat") Double lat, @RequestParam(name = "lon") Double lon) {
        return ResponseEntity.ok(productService.getProductsNearestToCoordinate(lat, lon, ""));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest productRequest) {
        Product createdProduct = productService.createProduct(productRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(createdProduct.getProductId()).toUri();
        return ResponseEntity.created(uri).body(createdProduct);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest product, @PathVariable Long productId) {
        boolean isCategoryUpdated = productService.updateProduct(product, productId);
        return  isCategoryUpdated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long productId) {
        if(!productService.doesEntityExist(productId)) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @RequestMapping(value = "/getByName")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam(name = "name") String name) {
        List<Product> filteredProducts = productService.getProductsByNameStartingWith(name);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping
    @RequestMapping(value = "/filter")
    public ResponseEntity<List<Product>> filterProducts(@RequestParam(name = "name") String name, @RequestParam(name = "lat") Double lat, @RequestParam(name = "lon") Double lon) {
        List<Product> filteredProducts = productService.getProductsNearestToCoordinate(lat, lon, name);
        return ResponseEntity.ok(filteredProducts);
    }

}
