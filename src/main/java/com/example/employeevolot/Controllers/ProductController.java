package com.example.employeevolot.Controllers;

import com.example.employeevolot.Models.*;
import com.example.employeevolot.Repository.CategoryRepository;
import com.example.employeevolot.Repository.MaterialRepository;
import com.example.employeevolot.Repository.ProductRepository;
import com.example.employeevolot.Repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubcategoryRepository subcategoryRepository;

    @GetMapping("")
    public String productMain(Model model) {
        Iterable<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "product/index";
    }

    @GetMapping("/add")
    public String productAddView(Product product, Model model) {
        model.addAttribute("listMaterial", listMaterial());
        model.addAttribute("listCategory", listCategory());
        model.addAttribute("listSubcategory", listSubcategory());
        return "product/action";
    }

    @PostMapping("/add")
    public String productAdd(@Valid Product product,
                             BindingResult result,
                             @RequestParam Long listMaterial,
                             @RequestParam Long listCategory, Model model) {
        model.addAttribute("listMaterial", listMaterial());
        model.addAttribute("listCategory", listCategory());
        model.addAttribute("listSubcategory", listSubcategory());

        if (result.hasErrors())
            return ("product/action");
        product.setMaterial(materialRepository.findById(listMaterial).orElseThrow());
        product.setSubcategory(subcategoryRepository.findById(listCategory).orElseThrow());
        product.setStatus(true);
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String productEdit(Model model,
                              @PathVariable long id) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("listMaterial", listMaterial());
        model.addAttribute("listCategory", listCategory());
        model.addAttribute("listSubcategory", listSubcategory());

        return ("product/edit");
    }

    @PostMapping("/edit/{id}")
    public String productEdit(@Valid Product product,
                              BindingResult result, Model model,
                              @RequestParam Long id,
                              @RequestParam Long listMaterial,
                              @RequestParam Long listCategory) {
        model.addAttribute("listMaterial", listMaterial());
        model.addAttribute("listCategory", listCategory());
        model.addAttribute("listSubcategory", listSubcategory());

        if (result.hasErrors())
            return ("/product/edit");

        product.setMaterial(materialRepository.findById(listMaterial).orElseThrow());
        product.setSubcategory(subcategoryRepository.findById(listCategory).orElseThrow());
        productRepository.save(product);
        return ("redirect:/product");
    }

    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setStatus(false);
        productRepository.save(product);
        return ("redirect:/product");
    }

    @GetMapping("/filter")
    public String productFilter(@RequestParam String searchName,
                                Model model) {
        List<Product> productList = productRepository.findByNameContaining(searchName);
        model.addAttribute("productList", productList);
        return "product/index";
    }

    Iterable<Material> listMaterial() {
        return materialRepository.findAll();
    }

    Iterable<Category> listCategory() {
        return categoryRepository.findAll();
    }


    Iterable<Subcategory> listSubcategory() {
        return subcategoryRepository.findAll();
    }
}
