package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Find tag by exact name
    Optional<Tag> findByName(String name);

    // Check if tag exists by name
    boolean existsByName(String name);
}