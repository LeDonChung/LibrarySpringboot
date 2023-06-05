package com.library.service.impl;

import com.library.dto.BookDto;
import com.library.dto.CommentDto;
import com.library.dto.UserDto;
import com.library.entity.Book;
import com.library.entity.Comment;
import com.library.entity.User;
import com.library.mapper.CommentMapper;
import com.library.mapper.UserMapper;
import com.library.repository.BookRepository;
import com.library.repository.CommentRepository;
import com.library.repository.UserRepository;
import com.library.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public CommentDto save(CommentDto dto, UserDto userDto, BookDto bookDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        Book book = bookRepository.findById(bookDto.getId()).get();
        Comment comment = commentMapper.toEntity(dto);
        comment.setCommentDate(new Date());
        comment.setUser(user);
        comment.setBook(book);
        return commentMapper.toDto(commentRepository.save(comment));
    }
}
