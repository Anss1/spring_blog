package com.anas.springblog.controller;

import com.anas.springblog.dto.CommentRequestDTO;
import com.anas.springblog.dto.CommentResponseDTO;
import com.anas.springblog.model.User;
import com.anas.springblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping
    public List<CommentResponseDTO> getAllComments() {
        return commentService.getAllComments();
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDTO> createComment( @RequestBody CommentRequestDTO commentRequest
                                                            , @AuthenticationPrincipal User userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(commentRequest,userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> editComment( @PathVariable Long id
                                        , @RequestBody CommentRequestDTO commentRequest
                                        , @AuthenticationPrincipal User userDetails) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.editComment(id,commentRequest,userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment( @PathVariable Long id
            , @AuthenticationPrincipal User userDetails) throws AccessDeniedException {
        commentService.deleteComment(id,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

}
