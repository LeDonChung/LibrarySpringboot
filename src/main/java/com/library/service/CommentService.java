package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.CommentDto;
import com.library.dto.UserDto;

public interface CommentService {
    CommentDto save(CommentDto dto, UserDto userDto, BookDto bookDto);
}
