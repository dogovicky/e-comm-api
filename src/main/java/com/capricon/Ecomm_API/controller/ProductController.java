package com.capricon.Ecomm_API.controller;

import com.capricon.Ecomm_API.model.Product;
import com.capricon.Ecomm_API.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        List<Product> products = service.getProducts();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/product/{title}")
    public ResponseEntity<Product> getProductByTitle(@PathVariable String title) {
        Product product = service.getProductByTitle(title);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchProducts(keyword));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterByCategory(@RequestParam String category) {
        return ResponseEntity.ok(service.filterByCategory(category));
    }

}
