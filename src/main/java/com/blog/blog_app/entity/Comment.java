package com.blog.blog_app.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    // ===== @ManyToOne: Comment belongs to ONE Post =====
    // The foreign key "post_id" lives in the "comments" table.
    // This is the OWNING side of the relationship.
    //
    // fetch = FetchType.LAZY:
    //   - Don't load the Post when loading a Comment
    //   - Load only when explicitly calling comment.getPost()
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;
    // ===== @ManyToOne: Comment written by ONE User =====
    // The foreign key "user_id" lives in the "comments" table.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User author;
    // ===== LIFECYCLE =====
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    // ===== CONVENIENCE CONSTRUCTOR =====
    public Comment(String content, Post post, User author) {
        this.content = content;
        this.post = post;
        this.author = author;
    }
    // ===== equals/hashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id != null && id.equals(comment.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    @Override
    public String toString() {
        return "Comment{id=" + id + ", content='" + content + "', createdAt=" + createdAt + "}";
    }
}