package com.anas.springblog.utility;

import com.anas.springblog.dto.CommentResponseDTO;
import com.anas.springblog.dto.PostResponseDTO;
import com.anas.springblog.model.Comment;
import com.anas.springblog.model.Post;
import org.springframework.stereotype.Component;

@Component
public class DtoUtil {

    public CommentResponseDTO commentResponse(Comment comment){
        return new CommentResponseDTO(
                comment.getId(),
                comment.getText(),
                comment.getUser().getUsername(),
                comment.getCreatedAt()
        );
    }

    public PostResponseDTO postResponse(Post post){
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getUsername(),
                post.getCreatedAt()
        );
    }
}
