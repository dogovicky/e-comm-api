package com.capricon.Ecomm_API.service;

import com.capricon.Ecomm_API.model.Product;
import com.capricon.Ecomm_API.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductByTitle(String title) {
        return repo.findByTitle(title);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Product> filterByCategory(String category) {
        return repo.findByCategory(category);
    }
}
