package com.blog.blog_app.repository;
import com.blog.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository provides:
    // - save(T entity)           → INSERT or UPDATE
    // - findById(Long id)       → SELECT by ID (returns Optional)
    // - findAll()               → SELECT all
    // - deleteById(Long id)     → DELETE by ID
    // - count()                 → COUNT(*)
    // - existsById(Long id)     → SELECT COUNT(*) WHERE id = ?
    // And many more...
}