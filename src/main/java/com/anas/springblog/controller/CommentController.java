package com.anas.springblog.controller;

import com.anas.springblog.dto.CommentResponseDTO;
import com.anas.springblog.model.Comment;
import com.anas.springblog.model.User;
import com.anas.springblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentResponseDTO> getAllComments() {
        return commentService.getAllComments()
                .stream()
                .map(this::commentDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CommentResponseDTO createComment(@RequestBody Comment comment, @AuthenticationPrincipal User userDetails) {
        comment.setUser(userDetails);
        Comment savedComment = commentService.createComment(comment);
        return commentDTO(savedComment);
    }

    private CommentResponseDTO commentDTO(Comment comment){
        return new CommentResponseDTO(
                comment.getId(),
                comment.getText(),
                comment.getUser().getUsername(),
                comment.getCreatedAt()
        );
    }
}
