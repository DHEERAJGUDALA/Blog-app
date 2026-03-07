package com.blog.blog_app.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Table(name = "users")  // "user" is a reserved word in PostgreSQL, always use "users"
@Getter
@Setter
@NoArgsConstructor       // JPA requires a no-arg constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL auto-increment
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    // === LIFECYCLE CALLBACK ===
    // This runs automatically before the entity is first saved to the database.
    // Better than setting it in the constructor — guarantees it's set even if
    // someone creates the object and forgets to call setCreatedAt().
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    // === PARAMETERIZED CONSTRUCTOR (for convenience) ===
    // We don't include 'id' or 'createdAt' because those are auto-generated.
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    // === equals() and hashCode() ===
    // IMPORTANT: We do NOT use Lombok's @EqualsAndHashCode for entities.
    //
    // Why? Lombok generates equals/hashCode using ALL fields by default.
    // If a relationship field is LAZY-loaded, accessing it in equals() triggers
    // a database query (or throws LazyInitializationException outside a transaction).
    //
    // Best practice: Use only the ID field, but handle the case where ID is null
    // (unsaved entities).
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }
    @Override
    public int hashCode() {
        // Return a constant so that the hash doesn't change when the entity gets
        // an ID after being saved. This is a well-known JPA best practice.
        // Performance impact is negligible unless you store thousands of entities
        // in a single HashSet (you won't).
        return getClass().hashCode();
    }
    @Override
    public String toString() {
        // Never include relationships (like posts) in toString() — causes infinite
        // recursion or lazy loading issues. Only include this entity's own fields.
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}