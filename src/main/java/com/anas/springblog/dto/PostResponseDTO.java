package com.anas.springblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String createdAt;

    public void setUpdatedAt(String updatedAt){
        this.createdAt = updatedAt;
    }
}
