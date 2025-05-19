package com.anas.springblog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}