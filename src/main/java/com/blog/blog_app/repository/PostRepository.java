package com.blog.blog_app.repository;
import com.blog.blog_app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // All CRUD methods come free from JpaRepository
}