package com.example.bookservice.controller.abstracts;
import com.example.bookservice.dto.BookRequest;
import com.example.bookservice.entities.Book;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BookController {
    List<Book> findAllBooks();
    Optional<Book> findByIdBook(Long id);
    Iterable<Book> searchByTitleandAuthor(Optional<String> title, Optional<String> author);
   void addBook(BookRequest bookRequest);
    void updateBook(Long id, BookRequest bookRequest);
    void deleteBook(Long id);
}
