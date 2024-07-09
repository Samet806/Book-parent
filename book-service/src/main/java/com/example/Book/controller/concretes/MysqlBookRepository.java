package com.example.Book.controller.concretes;
import com.example.Book.business.abstracts.BookService;
import com.example.Book.controller.abstracts.BookController;
import com.example.Book.dto.BookRequest;
import com.example.Book.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class MysqlBookRepository implements BookController {
    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public Optional<Book> findByIdBook(@PathVariable Long id){
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
    public Book addBook(@RequestBody BookRequest bookRequest) {
        return bookService.addBook(bookRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Book updateBook(@PathVariable Long id,@RequestBody BookRequest bookRequest) {
        return bookService.updateBook(id,bookRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
