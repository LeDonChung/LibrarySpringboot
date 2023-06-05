package com.library.dto;

import com.library.entity.Category;
import com.library.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Long id;

    private String title;

    private String author;

    private String bookCover;

    private Category category;

    private String releaseDate;

    private int numberOfPage;

    private int numberPay;

    private String description;
    private List<Comment> comments;

    private boolean is_activated;

    private boolean is_deleted;
}
