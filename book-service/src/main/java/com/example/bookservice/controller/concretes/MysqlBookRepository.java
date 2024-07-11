package com.example.bookservice.controller.concretes;

import com.example.bookservice.business.abstracts.BookService;
import com.example.bookservice.business.concretes.BookMessageSender;
import com.example.bookservice.controller.abstracts.BookController;
import com.example.bookservice.dto.BookMessage;
import com.example.bookservice.dto.BookRequest;
import com.example.bookservice.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class MysqlBookRepository implements BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookMessageSender bookMessageSender;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public Optional<Book> findByIdBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    @Override
    public Iterable<Book> searchByTitleandAuthor(@RequestParam Optional<String> title, @RequestParam Optional<String> author) {
        if (title.isPresent() && author.isPresent()) {
            return bookService.searchByTitleAndAuthor(title.get(), author.get());
        } else if (title.isPresent()) {
            return bookService.searchByTitle(title.get());
        } else if (author.isPresent()) {
            return bookService.searchByAuthor(author.get());
        } else {
            return bookService.findAllBooks();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void addBook(@RequestBody BookRequest bookRequest) {
        BookMessage bookMessage = new BookMessage();
        bookMessage.setAction("ADD");
        bookMessage.setBookRequest(bookRequest);
        bookMessageSender.sendMessage(bookMessage);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        BookMessage bookMessage = new BookMessage();
        bookMessage.setAction("UPDATE");
        bookMessage.setId(id);
        bookMessage.setBookRequest(bookRequest);
        bookMessageSender.sendMessage(bookMessage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteBook(@PathVariable Long id) {
        BookMessage bookMessage = new BookMessage();
        bookMessage.setAction("DELETE");
        bookMessage.setId(id);
        bookMessageSender.sendMessage(bookMessage);
    }
}
