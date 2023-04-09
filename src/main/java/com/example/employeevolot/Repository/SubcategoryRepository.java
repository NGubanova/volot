package com.example.employeevolot.Repository;

import com.example.employeevolot.Models.Subcategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {

    List<Subcategory> findByCategoryId(Long id);
}
