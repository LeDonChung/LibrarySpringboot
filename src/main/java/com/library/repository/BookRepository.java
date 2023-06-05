package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select b from Book b where b.is_activated = true and b.is_deleted = false")
    List<Book> findByActivated();
    @Query(value = "select b from Book b where b.title = ?1 and b.author = ?2")
    Book findByTitleAndAuthor(String title, String author);
}
