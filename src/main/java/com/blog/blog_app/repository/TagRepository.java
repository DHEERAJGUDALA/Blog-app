package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // === DERIVED QUERIES (from Step 8) ===
    Optional<Tag> findByName(String name);
    boolean existsByName(String name);

    // === JPQL QUERIES ===

    // Find tags with posts count
    @Query("SELECT t FROM Tag t JOIN t.posts p GROUP BY t ORDER BY COUNT(p) DESC")
    List<Tag> findTagsByPopularity();

    // Find tags containing name (case-insensitive)
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Tag> searchByName(@Param("name") String namePart);
}