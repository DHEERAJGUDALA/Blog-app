package com.blog.blog_app.repository;
import com.blog.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find by exact email
    Optional<User> findByEmail(String email);

    // Find users whose name contains the given string (case-insensitive)
    List<User> findByNameContainingIgnoreCase(String namePart);

    // Check if email exists
    boolean existsByEmail(String email);

    // Count users by name
    long countByName(String name);
}