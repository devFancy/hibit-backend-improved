package com.hibitbackendrefactor.post.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name = "post_images")
@Entity
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected PostImage() {
    }

    public PostImage(Post post, String imageUrl) {
        this.post = post;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
