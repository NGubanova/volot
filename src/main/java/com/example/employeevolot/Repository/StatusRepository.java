package com.example.employeevolot.Repository;

import com.example.employeevolot.Models.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long> {

    Status findByName(String name);
}
