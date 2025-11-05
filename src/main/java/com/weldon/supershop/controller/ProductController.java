package com.weldon.supershop.controller;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller to handle web requests related to products
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Injecting ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Display list of products
    @GetMapping
    public String viewProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // returns products.html
    }

    // Show form to create a new product
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form"; // returns product_form.html
    }

    // Handle form submission to save product
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products"; // redirect back to products list
    }

    // Show form to edit an existing product
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product_form";
    }
@GetMapping("/products")
public String productsPage() {
    return "products"; // Return products.html
}

    // Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
