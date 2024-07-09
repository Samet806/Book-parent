package com.example.Book.business.abstracts;

import com.example.Book.dto.BookRequest;
import com.example.Book.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

     List<Book> findAllBooks() ;
     Optional<Book> findById(Long id);
     Iterable<Book> searchByTitle(String title);
     Iterable<Book> searchByTitleAndAuthor(String s, String s1);
     Iterable<Book> searchByAuthor(String s);
     Book addBook(BookRequest bookRequest) ;
     Book updateBook(Long id, BookRequest bookRequest) ;
     void deleteBook(Long id) ;

}
