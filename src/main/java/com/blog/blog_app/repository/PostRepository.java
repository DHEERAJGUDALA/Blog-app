package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Post;
import com.blog.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Find all posts by author
    List<Post> findByAuthor(User author);

    // Find all posts by author ID
    List<Post> findByAuthorId(Long authorId);

    // Find all published posts
    List<Post> findByPublishedTrue();

    // Find all unpublished posts
    List<Post> findByPublishedFalse();

    // Find posts containing title (case-insensitive)
    List<Post> findByTitleContainingIgnoreCase(String titlePart);

    // Find posts by author and published status
    List<Post> findByAuthorIdAndPublished(Long authorId, boolean published);
}