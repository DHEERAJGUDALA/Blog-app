package com.blog.blog_app.repository;
import com.blog.blog_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // === DERIVED QUERIES ===
    Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String namePart);
    boolean existsByEmail(String email);
    long countByName(String name);

    // === JPQL QUERIES ===
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailUsingJpql(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> searchByNameContaining(@Param("name") String namePart);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:search) OR LOWER(u.email) = LOWER(:search)")
    List<User> searchByNameOrEmail(@Param("search") String searchTerm);

    // === PAGINATION & SORTING ===

    // Paginated and sorted users
    Page<User> findAll(Pageable pageable);

    // Sorted by name
    List<User> findAll(Sort sort);
}