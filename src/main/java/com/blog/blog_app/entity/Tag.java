package com.blog.blog_app.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
    // ===== @ManyToMany: A tag can belong to MANY posts =====
    // This is the NON-OWNING side (Post is the owner).
    // "mappedBy" means the "tags" field in Post OWNS this relationship.
    //
    // fetch = FetchType.LAZY:
    //   - Don't load posts when loading a tag
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    // ===== CONVENIENCE CONSTRUCTOR =====
    public Tag(String name) {
        this.name = name;
    }
    // ===== equals/hashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id != null && id.equals(tag.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    @Override
    public String toString() {
        return "Tag{id=" + id + ", name='" + name + "'}";
    }
}