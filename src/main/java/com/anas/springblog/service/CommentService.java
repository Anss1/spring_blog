package com.anas.springblog.service;

import com.anas.springblog.dto.CommentRequestDTO;
import com.anas.springblog.dto.CommentResponseDTO;
import com.anas.springblog.model.Comment;
import com.anas.springblog.model.User;
import com.anas.springblog.repository.CommentRepository;
import com.anas.springblog.utility.DateAndTime;
import com.anas.springblog.utility.DtoUtil;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private DtoUtil dto;

    public List<CommentResponseDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(c -> dto.commentResponse(c))
                .toList();
    }

    public CommentResponseDTO createComment(CommentRequestDTO commentRequest, User userDetails) {
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setUser(userDetails);
        comment.setCreatedAt(DateAndTime.now());
        Comment savedComment = commentRepository.save(comment);
        return dto.commentResponse(savedComment);
    }

    public CommentResponseDTO editComment(Long id, CommentRequestDTO commentRequest, User userDetails) throws AccessDeniedException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Comment.class,"Comment not found"));
        if (!comment.getUser().getId().equals(userDetails.getId())){
            throw new AccessDeniedException("Not authorized");
        }
        comment.setText(commentRequest.getText());
        comment.setUpdatedAt(DateAndTime.now());

        Comment updatedComment = commentRepository.save(comment);
        return dto.commentResponse(updatedComment);
    }

    public void deleteComment(Long id, User userDetails) throws AccessDeniedException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Comment.class,"Comment not found"));
        if (!comment.getUser().getId().equals(userDetails.getId())){
            throw new AccessDeniedException("Not authorized");
        }
        commentRepository.delete(comment);
    }
}
