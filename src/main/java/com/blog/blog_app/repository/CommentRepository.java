package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Comment;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // === DERIVED QUERIES ===
    List<Comment> findByPost(Post post);
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthor(User author);
    List<Comment> findByAuthorId(Long authorId);
    long countByPostId(Long postId);

    // === JPQL QUERIES ===
    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    List<Comment> findRecentComments();

    @Query("SELECT c FROM Comment c WHERE c.post.id IN :postIds")
    List<Comment> findByPostIds(@Param("postIds") List<Long> postIds);

    @Query("SELECT c.post.id, COUNT(c) FROM Comment c GROUP BY c.post.id")
    List<Object[]> countCommentsPerPost();

    // === PAGINATION & SORTING ===

    // Paginated comments for a post
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}