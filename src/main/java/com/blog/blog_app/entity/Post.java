package com.blog.blog_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "published", nullable = false)
    private boolean published = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== @ManyToOne: A post has ONE author (User) =====
    // "Many posts belong to one user" = @ManyToOne
    //
    // fetch = FetchType.LAZY:
    //   - When you load a Post, do NOT load the author from DB yet
    //   - Only load when you call post.getAuthor()
    //   - This is CRITICAL for performance — prevents unnecessary queries
    //
    // @JoinColumn:
    //   - name = "user_id" → creates foreign key column in "posts" table
    //   - nullable = false → every post must have an author
    //   - foreignKey → explicit constraint name for debugging
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, 
                foreignKey = @ForeignKey(name = "fk_post_user"))
    private User author;

    // ===== @OneToMany: A post can have MANY comments =====
    // This is the NON-OWNING side (Comment has the foreign key).
    // "mappedBy" means the "post" field in Comment OWNS this relationship.
    //
    // cascade = CascadeType.ALL:
    //   - When saving/deleting a Post, automatically save/delete its Comments
    //
    // orphanRemoval = true:
    //   - If a Comment is removed from this list, delete it from DB
    //
    // fetch = FetchType.LAZY:
    //   - Don't load comments when loading a Post
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // ===== @ManyToMany: A post can have MANY tags =====
    // This is the OWNING side — defines the join table
    //
    // @JoinTable:
    //   - name = "post_tags" → join table name
    //   - joinColumns = post_id → this entity's column in join table
    //   - inverseJoinColumns = tag_id → the Tag's column in join table
    // cascade PERSIST & MERGE only — saving a post saves new tags too,
    // but deleting a post does NOT delete tags (other posts may use them)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    // ===== LIFECYCLE CALLBACKS =====
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== CONVENIENCE CONSTRUCTOR =====
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // ===== equals/hashCode (same pattern as User) =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id != null && id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        // Never include relationships in toString() — causes lazy loading or infinite recursion
        return "Post{id=" + id + ", title='" + title + "', published=" + published + "}";
    }
}
