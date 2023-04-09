package com.example.employeevolot.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<Subcategory> subcategories;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Collection<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}
