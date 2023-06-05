package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.CommentDto;
import com.library.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    BookDto save(BookDto book, MultipartFile bookCover);
    BookDto findById(Long id);

    List<BookDto> findAll();

    List<BookDto> findAllByActivated();


    void delete(Long id);

    void enable(Long id);

    BookDto findByTitleAndAuthor(String title, String author);

}
