package com.weldon.supershop.controller;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.model.Category;
import com.weldon.supershop.repository.ProductRepository;
import com.weldon.supershop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // --- List all products ---
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products"; // matches products.html
    }

    // --- Show add product form ---
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        Product product = new Product(); // empty product
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "productform"; // matches productform.html
    }

    // --- Show edit product form ---
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            List<Category> categories = categoryRepository.findAll();

            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            return "productform";
        } else {
            return "redirect:/products";
        }
    }

    // --- Save new or edited product ---
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    // --- Delete product ---
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
