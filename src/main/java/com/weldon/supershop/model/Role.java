// File: src/main/java/com/supershop/model/Role.java

// File: src/main/java/com/weldon/supershop/model/Role.java

package com.weldon.supershop.model; // Organized under the main project package for the model layer

// Import JPA annotations to map this class as a database entity
import jakarta.persistence.Entity; // Marks this class as a persistent JPA entity
import jakarta.persistence.GeneratedValue; // Allows automatic primary key generation
import jakarta.persistence.GenerationType; // Specifies how the ID should be generated (IDENTITY = auto-increment)
import jakarta.persistence.Id; // Marks the field as the primary key
import jakarta.persistence.Table; // Maps the entity to a specific database table

@Entity // Tells JPA this class should be stored in the database
@Table(name = "roles") // Specifies the database table name as 'roles'
public class Role {

    @Id // Declares 'id' as the primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID in the database
    private Long id; // Stores unique identifier for each role (e.g., 1 = ADMIN)

    private String name; // Stores the role name (e.g., ADMIN, CUSTOMER, SELLER)

    // ==========================
    // Constructors Section
    // ==========================

    public Role() {} // Default constructor required by JPA

    // Parameterized constructor to create roles easily
    public Role(String name) {
        this.name = name; // Initializes role name
    }

    // ==========================
    // Getters and Setters Section
    // ==========================

    public Long getId() { // Returns the role's unique ID
        return id;
    }

    public void setId(Long id) { // Sets or updates the role's ID
        this.id = id;
    }

    public String getName() { // Returns the role's name
        return name;
    }

    public void setName(String name) { // Sets or updates the role's name
        this.name = name;
    }

    // ==========================
    // toString Method
    // ==========================

    @Override
    public String toString() { // Provides a readable string form of the role object
        return "Role{id=" + id + ", name='" + name + "'}";
    }
}
