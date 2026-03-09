package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Post;
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
public interface PostRepository extends JpaRepository<Post, Long> {

    // === DERIVED QUERIES ===
    List<Post> findByAuthor(User author);
    List<Post> findByAuthorId(Long authorId);
    List<Post> findByPublishedTrue();
    List<Post> findByPublishedFalse();
    List<Post> findByTitleContainingIgnoreCase(String titlePart);
    List<Post> findByAuthorIdAndPublished(Long authorId, boolean published);

    // === JPQL QUERIES ===
    @Query("SELECT p FROM Post p WHERE p.published = true ORDER BY p.createdAt DESC")
    List<Post> findAllPublishedPosts();

    @Query("SELECT p FROM Post p WHERE p.author.email = :email")
    List<Post> findByAuthorEmail(@Param("email") String email);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% AND p.published = true")
    List<Post> searchPublishedPostsByTitle(@Param("keyword") String keyword);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.author WHERE p.id = :id")
    Optional<Post> findByIdWithAuthor(@Param("id") Long id);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.author.id = :authorId")
    long countPostsByAuthorId(@Param("authorId") Long authorId);

    // === PAGINATION & SORTING ===

    // Paginated & sorted by custom field
    Page<Post> findByPublishedTrue(Pageable pageable);

    // Paginated posts by author
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);

    // Paginated search by title
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    Page<Post> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    // All posts sorted
    List<Post> findAll(Sort sort);

    // Get posts with pagination in one method
    @Query("SELECT p FROM Post p WHERE p.published = :published")
    Page<Post> findByPublished(@Param("published") boolean published, Pageable pageable);
}