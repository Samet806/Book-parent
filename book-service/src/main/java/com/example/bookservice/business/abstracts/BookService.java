package com.example.bookservice.business.abstracts;

import com.example.bookservice.dto.BookRequest;
import com.example.bookservice.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
     List<Book> findAllBooks();
     Optional<Book> findById(Long id);
     Iterable<Book> searchByTitle(String title);
     Iterable<Book> searchByAuthor(String author);
     Iterable<Book> searchByTitleAndAuthor(String title, String author);

     void processAddBook(BookRequest bookRequest);
     void processUpdateBook(Long id, BookRequest bookRequest);
     void processDeleteBook(Long id);
}
