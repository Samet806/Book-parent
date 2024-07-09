package com.example.Book.controller.abstracts;

import com.example.Book.dto.BookRequest;
import com.example.Book.entities.Book;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;


public interface BookController {
    List<Book> findAllBooks() ;
    Optional<Book> findByIdBook(Long id);
    Iterable<Book> searchByTitleandAuthor(Optional<String>  title,Optional<String> author);
    Book addBook(BookRequest bookRequest) ;
    Book updateBook(Long id, BookRequest book) ;
    void deleteBook(Long id) ;
}
