package com.library.mapper;

import com.library.dto.CommentDto;
import com.library.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setStars(entity.getStars());
        dto.setCommentDate(entity.getCommentDate());
        if(entity.getBook() != null) {
            dto.setBook(entity.getBook());
        }

        if(entity.getUser() != null) {
            dto.setUser(entity.getUser());
        }
        return dto;
    }
    public Comment toEntity(CommentDto dto) {
        Comment entity = new Comment();
        entity.setContent(dto.getContent());
        entity.setStars(dto.getStars());
        if(dto.getBook() != null) {
            entity.setBook(dto.getBook());
        }

        if(dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
        return entity;
    }

}
