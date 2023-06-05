package com.library.service.impl;

import com.library.dto.BookDto;
import com.library.dto.CommentDto;
import com.library.dto.UserDto;
import com.library.entity.Book;
import com.library.entity.Comment;
import com.library.entity.User;
import com.library.mapper.BookMapper;
import com.library.mapper.CommentMapper;
import com.library.repository.BookRepository;
import com.library.repository.CommentRepository;
import com.library.repository.UserRepository;
import com.library.service.BookService;
import com.library.utils.ImageUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ImageUploadUtils imageUploadUtils;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public BookDto save(BookDto bookDto, MultipartFile bookCover) {
        Book book = bookMapper.toEntity(bookDto);
        if(bookDto.getId() != null) {
            Book bookOld = bookRepository.findById(bookDto.getId()).get();
            book = bookMapper.toEntity(bookOld, bookDto);
            try {
                if (bookCover.getSize() != 0) {
                    // exits
                    if (!imageUploadUtils.checkExisted(bookCover)) {
                        imageUploadUtils.uploadImage(bookCover);
                    }
                    book.setBookCover(Base64.getEncoder().encodeToString(bookCover.getBytes()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (bookCover.getSize() == 0) {
                    book.setBookCover(null);
                } else {
                    imageUploadUtils.uploadImage(bookCover);
                    book.setBookCover(Base64.getEncoder().encodeToString(bookCover.getBytes()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            book.set_deleted(false);
            book.set_activated(true);
        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).get();
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        List<BookDto> results = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        for (Book book: books) {
            results.add(bookMapper.toDto(book));
        }
        return results;
    }

    @Override
    public List<BookDto> findAllByActivated() {
        List<BookDto> results = new ArrayList<>();
        List<Book> books = bookRepository.findByActivated();
        for (Book book: books) {
            results.add(bookMapper.toDto(book));
        }
        return results;
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).get();
        book.set_activated(false);
        book.set_deleted(true);
        bookRepository.save(book);
    }

    @Override
    public void enable(Long id) {
        Book book = bookRepository.findById(id).get();
        book.set_activated(true);
        book.set_deleted(false);
        bookRepository.save(book);
    }

    @Override
    public BookDto findByTitleAndAuthor(String title, String author) {
        Book book = bookRepository.findByTitleAndAuthor(title, author);
        if(book == null) {
            return null;
        }
        return bookMapper.toDto(book);
    }
}
