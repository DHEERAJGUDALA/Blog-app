package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Comment;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find all comments for a specific post
    List<Comment> findByPost(Post post);

    // Find all comments for a specific post ID
    List<Comment> findByPostId(Long postId);

    // Find all comments by a specific user
    List<Comment> findByAuthor(User author);

    // Find all comments by a specific user ID
    List<Comment> findByAuthorId(Long authorId);

    // Count comments for a specific post
    long countByPostId(Long postId);
}