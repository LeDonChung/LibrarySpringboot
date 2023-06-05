package com.library.dto;

import com.library.entity.Book;
import com.library.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;

    private User user;

    private Book book;

    private String content;

    private Date commentDate;

    private Integer stars;
}
