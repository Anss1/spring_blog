package com.anas.springblog.service;

import com.anas.springblog.dto.CommentResponseDTO;
import com.anas.springblog.model.Comment;
import com.anas.springblog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

}
