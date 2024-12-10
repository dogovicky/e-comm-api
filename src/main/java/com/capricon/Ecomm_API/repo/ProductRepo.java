package com.capricon.Ecomm_API.repo;

import com.capricon.Ecomm_API.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

    //custom method to find product by id
    //Product findByProduct_Id(String product_id);
    Product findByTitle(String title);

    //search and filter functionality
    List<Product> findByTitleContainingIgnoreCase(String title);
    List<Product> findByCategory(String category);

}
