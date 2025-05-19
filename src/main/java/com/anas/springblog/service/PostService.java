package com.anas.springblog.service;

import com.anas.springblog.model.Post;
import com.anas.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;


    public Post createPost(Post post, String name) {
        // TODO
        return new Post();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
