package com.anas.springblog.controller;

import com.anas.springblog.dto.PostRequestDTO;
import com.anas.springblog.dto.PostResponseDTO;
import com.anas.springblog.model.User;
import com.anas.springblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping
    public List<PostResponseDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequest
                                                , @AuthenticationPrincipal User userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(postRequest,userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> editPost(@PathVariable Long id, @RequestBody PostRequestDTO postRequest,
                                    @AuthenticationPrincipal User userDetails) throws AccessDeniedException {
        return ResponseEntity.ok(postService.editPost(id,postRequest,userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id
            , @AuthenticationPrincipal User userDetails) throws AccessDeniedException {
         postService.deletePost(id,userDetails);
         return ResponseEntity.ok("Deleted successfully");
    }
}