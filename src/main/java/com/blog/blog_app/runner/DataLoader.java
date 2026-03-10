package com.blog.blog_app.runner;
import com.blog.blog_app.entity.*;
import com.blog.blog_app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
// @Component makes this a Spring bean — Spring will find it and run it at startup
@Component
public class DataLoader implements CommandLineRunner {
    // Constructor injection (best practice — no @Autowired needed on single constructor)
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    public DataLoader(UserRepository userRepository,
                      PostRepository postRepository,
                      CommentRepository commentRepository,
                      TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
    }
    @Override
    @Transactional  // Wraps everything in a single database transaction
    public void run(String... args) throws Exception {
        // ===== 1. CREATE USERS =====
        System.out.println("===== CREATING USERS =====");
        User sai = new User("Sai", "sai@gmail.com", "password123");
        User rahul = new User("Rahul", "rahul@gmail.com", "password456");
        userRepository.save(sai);
        userRepository.save(rahul);
        System.out.println("Saved: " + sai);
        System.out.println("Saved: " + rahul);
        // ===== 2. CREATE TAGS =====
        System.out.println("\n===== CREATING TAGS =====");
        Tag javaTag = new Tag("java");
        Tag springTag = new Tag("spring");
        Tag jpaTag = new Tag("jpa");
        tagRepository.save(javaTag);
        tagRepository.save(springTag);
        tagRepository.save(jpaTag);
        System.out.println("Saved: " + javaTag);
        System.out.println("Saved: " + springTag);
        System.out.println("Saved: " + jpaTag);
        // ===== 3. CREATE POSTS WITH TAGS =====
        System.out.println("\n===== CREATING POSTS =====");
        Post post1 = new Post("Learning JPA", "JPA is an ORM framework...", sai);
        post1.setPublished(true);
        post1.getTags().add(javaTag);
        post1.getTags().add(jpaTag);
        Post post2 = new Post("Spring Boot Guide", "Spring Boot simplifies...", sai);
        post2.setPublished(true);
        post2.getTags().add(springTag);
        post2.getTags().add(javaTag);
        Post post3 = new Post("Draft Post", "This is not published yet...", rahul);
        // post3.published = false (default)
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        System.out.println("Saved: " + post1);
        System.out.println("Saved: " + post2);
        System.out.println("Saved: " + post3);
        // ===== 4. CREATE COMMENTS =====
        System.out.println("\n===== CREATING COMMENTS =====");
        Comment comment1 = new Comment("Great article on JPA!", post1, rahul);
        Comment comment2 = new Comment("Very helpful, thanks!", post1, sai);
        Comment comment3 = new Comment("Nice Spring Boot guide!", post2, rahul);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        System.out.println("Saved: " + comment1);
        System.out.println("Saved: " + comment2);
        System.out.println("Saved: " + comment3);
        // ===== 5. TEST DERIVED QUERIES =====
        System.out.println("\n===== TESTING DERIVED QUERIES =====");
        // Find user by email
        userRepository.findByEmail("sai@gmail.com")
                .ifPresent(user -> System.out.println("Found by email: " + user));
        // Find published posts
        List<Post> publishedPosts = postRepository.findByPublishedTrue();
        System.out.println("Published posts: " + publishedPosts.size());
        // Find posts by author
        List<Post> saiPosts = postRepository.findByAuthorId(sai.getId());
        System.out.println("Sai's posts: " + saiPosts.size());
        // Find comments on post1
        List<Comment> post1Comments = commentRepository.findByPostId(post1.getId());
        System.out.println("Comments on post1: " + post1Comments.size());
        // Count comments per post
        long commentCount = commentRepository.countByPostId(post1.getId());
        System.out.println("Comment count on post1: " + commentCount);
        // ===== 6. TEST JPQL QUERIES =====
        System.out.println("\n===== TESTING JPQL QUERIES =====");
        // Search published posts by title
        List<Post> searchResults = postRepository.searchPublishedPostsByTitle("JPA");
        System.out.println("Search 'JPA' in published posts: " + searchResults.size());
        // Find posts by author email
        List<Post> postsByEmail = postRepository.findByAuthorEmail("sai@gmail.com");
        System.out.println("Posts by sai@gmail.com: " + postsByEmail.size());
        // Count posts by author
        long saiPostCount = postRepository.countPostsByAuthorId(sai.getId());
        System.out.println("Sai's total posts: " + saiPostCount);
        // ===== 7. TEST PAGINATION =====
        System.out.println("\n===== TESTING PAGINATION =====");
        // Page 0, 2 posts per page, sorted by createdAt descending
        Page<Post> postPage = postRepository.findByPublishedTrue(
                PageRequest.of(0, 2, Sort.by("createdAt").descending())
        );
        System.out.println("Page content: " + postPage.getContent());
        System.out.println("Total elements: " + postPage.getTotalElements());
        System.out.println("Total pages: " + postPage.getTotalPages());
        System.out.println("Current page: " + postPage.getNumber());
        System.out.println("Has next: " + postPage.hasNext());
        // ===== 8. TEST UPDATE =====
        System.out.println("\n===== TESTING UPDATE =====");
        post3.setPublished(true);
        post3.setTitle("Updated Draft Post");
        postRepository.save(post3);  // save() does UPDATE when entity has an ID
        System.out.println("Updated: " + post3);
        // ===== 9. TEST DELETE =====
        System.out.println("\n===== TESTING DELETE =====");
        long beforeDelete = postRepository.count();
        postRepository.deleteById(post3.getId());
        long afterDelete = postRepository.count();
        System.out.println("Posts before delete: " + beforeDelete + ", after: " + afterDelete);
        System.out.println("\n===== ALL TESTS PASSED =====");
    }
}