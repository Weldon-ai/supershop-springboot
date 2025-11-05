// ===============================
// ========================================
// File: User.java
// Package: com.weldon.supershop.model
// Description: Represents the user entity with username + multiple roles
// ========================================

package com.weldon.supershop.model; // Defines package location

// ===============================
// Import Statements
// ===============================
import jakarta.persistence.*; // For JPA annotations and ORM mapping
import java.util.HashSet; // To avoid duplicate roles
import java.util.Set; // To store multiple user roles

// ===============================
// Entity Definition
// ===============================
@Entity // Marks this class as a database entity
@Table(name = "users") // Table name in MySQL will be 'users'
public class User {

    // ===============================
    // Primary Key
    // ===============================
    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private Long id; // Unique user identifier

    // ===============================
    // Basic Information
    // ===============================
    @Column(nullable = false, unique = true) // Username must be unique
    private String username; // Used for login and authentication

    @Column(nullable = false) // Full name cannot be null
    private String fullName; // User's full name

    @Column(nullable = false, unique = true) // Unique email for communication
    private String email; // User's email address

    @Column(nullable = false) // Password must be provided
    private String password; // Encrypted password (BCrypt recommended)

    @Column // Optional
    private String phone; // User’s phone number

    @Column // Optional
    private String address; // Delivery or billing address

    // ===============================
    // Roles Relationship
    // ===============================
    @ManyToMany(fetch = FetchType.EAGER) // Load roles whenever user is loaded
    @JoinTable(
        name = "user_roles", // Join table linking users and roles
        joinColumns = @JoinColumn(name = "user_id"), // FK to user
        inverseJoinColumns = @JoinColumn(name = "role_id") // FK to role
    )
    private Set<Role> roles = new HashSet<>(); // Each user can have multiple roles

    // ===============================
    // Constructors
    // ===============================
    public User() {} // Default constructor required by JPA

    // Constructor with full details
    public User(String username, String fullName, String email, String password, String phone, String address) {
        this.username = username; // Assign username
        this.fullName = fullName; // Assign full name
        this.email = email; // Assign email
        this.password = password; // Assign password
        this.phone = phone; // Assign phone
        this.address = address; // Assign address
    }

    // ===============================
    // Getter and Setter Methods
    // ===============================

    public Long getId() { // Returns user ID
        return id;
    }

    public void setId(Long id) { // Sets user ID
        this.id = id;
    }

    public String getUsername() { // Returns username
        return username;
    }

    public void setUsername(String username) { // Sets username
        this.username = username;
    }

    public String getFullName() { // Returns full name
        return fullName;
    }

    public void setFullName(String fullName) { // Sets full name
        this.fullName = fullName;
    }

    public String getEmail() { // Returns email
        return email;
    }

    public void setEmail(String email) { // Sets email
        this.email = email;
    }

    public String getPassword() { // Returns password
        return password;
    }

    public void setPassword(String password) { // Sets password
        this.password = password;
    }

    public String getPhone() { // Returns phone number
        return phone;
    }

    public void setPhone(String phone) { // Sets phone number
        this.phone = phone;
    }

    public String getAddress() { // Returns address
        return address;
    }

    public void setAddress(String address) { // Sets address
        this.address = address;
    }

    public Set<Role> getRoles() { // Returns roles
        return roles;
    }

    public void setRoles(Set<Role> roles) { // Sets roles
        this.roles = roles;
    }

    // ✅ This replaces getRole(): return single role name for Spring Security
    public String getPrimaryRole() {
        return roles.stream()
                .findFirst()
                .map(Role::getName)
                .orElse("ROLE_USER");
    }

    // ===============================
    // Utility Method
    // ===============================
    public void addRole(Role role) { // Add one role to a user
        this.roles.add(role);
    }

    // ===============================
    // toString() for Debugging
    // ===============================
    @Override
    public String toString() { // Converts object to readable format
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", roles=" + roles +
                '}';
    }
}
