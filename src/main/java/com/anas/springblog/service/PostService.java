package com.anas.springblog.service;

import com.anas.springblog.model.Post;
import com.anas.springblog.model.User;
import com.anas.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserService userService;


    public Post createPost(Post post, Long userId) {
        User user = userService.loadUserByID(userId);
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
