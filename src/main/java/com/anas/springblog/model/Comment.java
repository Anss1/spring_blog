package com.anas.springblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
}