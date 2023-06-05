package com.library.mapper;

import com.library.dto.BookDto;
import com.library.entity.Book;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class BookMapper {
    public BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setAuthor(entity.getAuthor());
        dto.setNumberPay(entity.getNumberPay());
        dto.setNumberOfPage(entity.getNumberOfPage());
        dto.set_activated(entity.is_activated());
        dto.set_deleted(entity.is_deleted());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if(entity.getReleaseDate() != null) {
            dto.setReleaseDate(formatter.format(entity.getReleaseDate()));
        }
        if(entity.getComments() != null) {
            dto.setComments(entity.getComments());
        }

        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setBookCover(entity.getBookCover());
        if(entity.getCategory() != null) {
            dto.setCategory(entity.getCategory());
        }
        return dto;
    }
    public Book toEntity(BookDto dto) {
        Book entity = new Book();
        entity.setAuthor(dto.getAuthor());
        entity.setNumberOfPage(dto.getNumberOfPage());
        entity.setNumberPay(dto.getNumberPay());
        entity.set_activated(dto.is_activated());
        entity.set_deleted(dto.is_deleted());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if(dto.getReleaseDate() != null) {
            try {
                if(!dto.getReleaseDate().isBlank()) {
                    entity.setReleaseDate(formatter.parse(dto.getReleaseDate()));
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }

        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setBookCover(dto.getBookCover());
        if(dto.getComments() != null) {
            entity.setComments(dto.getComments());
        }
        if(dto.getCategory() != null) {
            entity.setCategory(dto.getCategory());
        }
        return entity;
    }
    public Book toEntity(Book entityNew, BookDto dto) {
        entityNew.setAuthor(dto.getAuthor());
        entityNew.setNumberOfPage(dto.getNumberOfPage());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if(dto.getReleaseDate() != null) {
            try {
                entityNew.setReleaseDate(formatter.parse(dto.getReleaseDate()));
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        entityNew.setDescription(dto.getDescription());
        entityNew.setTitle(dto.getTitle());
        if(dto.getCategory() != null) {
            entityNew.setCategory(dto.getCategory());
        }
        return entityNew;
    }
}
