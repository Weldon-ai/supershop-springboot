package com.weldon.supershop.controller;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.model.LoginLog;
import com.weldon.supershop.model.Order;
import com.weldon.supershop.repository.ProductRepository;
import com.weldon.supershop.repository.OrderRepository;
import com.weldon.supershop.repository.LoginLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LoginLogRepository loginLogRepository;

    // -------------------------
    // Admin Dashboard
    // -------------------------
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<Product> products = productRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
        long totalCustomers = orders.stream()
                .map(Order::getCustomerEmail)
                .distinct()
                .count();

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalCustomers", totalCustomers);

        return "admin_dashboard";
    }

    // -------------------------
    // Products Management
    // -------------------------
    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin_products";
    }

    @GetMapping({"/products/add", "/products/edit/{id}"})
    public String showProductForm(@PathVariable(required = false) Long id, Model model) {
        Product product = (id != null)
                ? productRepository.findById(id).orElse(new Product())
                : new Product();
        model.addAttribute("product", product);
        return "productform";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images/");
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            Files.copy(file.getInputStream(), uploadPath.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            product.setImageUrl("/images/" + filename);
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    // -------------------------
    // Reports / Orders
    // -------------------------
    @GetMapping("/reports")
    public String viewReports(Model model) {
        List<Order> orders = orderRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        orders.forEach(order -> {
            if (order.getOrderDate() != null) {
                order.setFormattedDate(order.getOrderDate().format(formatter));
            } else {
                order.setFormattedDate("N/A");
            }
        });

        model.addAttribute("orders", orders);
        return "admin_reports";
    }

    // -------------------------
    // Active Users (with session duration)
    // -------------------------
    @GetMapping("/active-users")
    public String viewActiveUsers(Model model) {
        try {
            List<LoginLog> activeUsers = loginLogRepository.findAll()
                    .stream()
                    .filter(log -> log.getLoginTime() != null && log.getLogoutTime() == null)
                    .collect(Collectors.toList());

            activeUsers.forEach(this::setSessionDuration);
            model.addAttribute("activeUsers", activeUsers);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("activeUsers", List.of());
        }

        return "active_users";
    }

    private void setSessionDuration(LoginLog log) {
        try {
            if (log.getLoginTime() != null) {
                LocalDateTime endTime = (log.getLogoutTime() != null)
                        ? log.getLogoutTime()
                        : LocalDateTime.now();
                Duration duration = Duration.between(log.getLoginTime(), endTime);
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;
                log.setSessionDuration(String.format("%02dh:%02dm:%02ds", hours, minutes, seconds));
            } else {
                log.setSessionDuration("N/A");
            }
        } catch (Exception e) {
            log.setSessionDuration("Error");
        }
    }

    // -------------------------
    // Login Logs
    // -------------------------
    @GetMapping("/loginlogs")
    public String viewLoginLogs(Model model) {
        model.addAttribute("logs", loginLogRepository.findAll());
        return "loginlogs";
    }
}
