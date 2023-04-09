package com.example.employeevolot.Repository;

import com.example.employeevolot.Models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
}
