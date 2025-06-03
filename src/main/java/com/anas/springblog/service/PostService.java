package com.anas.springblog.service;

import com.anas.springblog.dto.PostRequestDTO;
import com.anas.springblog.dto.PostResponseDTO;
import com.anas.springblog.model.Post;
import com.anas.springblog.model.User;
import com.anas.springblog.repository.PostRepository;
import com.anas.springblog.utility.DateAndTime;
import com.anas.springblog.utility.DtoUtil;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    private DtoUtil dtoUtil;


    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(p -> dtoUtil.postResponse(p))
                .toList();
    }

    public PostResponseDTO createPost(PostRequestDTO postRequest, User userDetails) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCreatedAt(DateAndTime.now());
        post.setUser(userDetails);
        Post savedPost = postRepository.save(post);
        return dtoUtil.postResponse(savedPost);
    }

    public PostResponseDTO editPost(Long id, PostRequestDTO postRequest, User userDetails) throws AccessDeniedException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Post.class,"Post not found"));
        if(!post.getUser().getId().equals(userDetails.getId())){
            throw new AccessDeniedException("Not authorized");
        }
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUpdatedAt(DateAndTime.now());

        Post updatedPost = postRepository.save(post);
        return dtoUtil.postResponse(updatedPost);
    }

    public void deletePost(Long id, User userDetails) throws AccessDeniedException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Post.class,"Post not found"));
        if(!post.getUser().getId().equals(userDetails.getId())){
            throw new AccessDeniedException("Not authorized");
        }
        postRepository.delete(post);
    }
}
