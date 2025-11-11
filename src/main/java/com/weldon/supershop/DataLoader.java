package com.weldon.supershop;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if(productRepository.count() == 0) {
            productRepository.save(new Product("Men's Casual Shirt", "Men", "Comfortable cotton shirt", 1500.0, "https://images.unsplash.com/photo-1593032457861-7c8f880b4fc1"));
            productRepository.save(new Product("Women's Summer Dress", "Women", "Floral summer dress", 2000.0, "https://images.unsplash.com/photo-1520975698511-1be2f0de31d3"));
            productRepository.save(new Product("Men's Running Shoes", "Shoes", "Lightweight running shoes", 3000.0, "https://images.unsplash.com/photo-1528701800483-3c8d0b30336d"));
            productRepository.save(new Product("Kids T-Shirt", "Kids", "Soft cotton t-shirt", 1000.0, "https://images.unsplash.com/photo-1618354695700-78d8f4e1d0a2"));
        }
    }
}
