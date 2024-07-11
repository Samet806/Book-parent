package com.example.bookservice.business.concretes;

import com.example.bookservice.business.abstracts.BookService;
import com.example.bookservice.dto.BookRequest;
import com.example.bookservice.entities.Book;
import com.example.bookservice.repository.elasticsearch.BookElasticsearchRepository;
import com.example.bookservice.repository.jpa.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookManager implements BookService {

    private final BookElasticsearchRepository bookElasticsearchRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Iterable<Book> searchByTitle(String title) {
        return bookElasticsearchRepository.findByTitle(title);
    }

    @Override
    public Iterable<Book> searchByAuthor(String author) {
        return bookElasticsearchRepository.findByAuthor(author);
    }

    @Override
    public Iterable<Book> searchByTitleAndAuthor(String title, String author) {
        return bookElasticsearchRepository.findByTitleAndAuthor(title, author);
    }

    @Override
    public void processAddBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .price(bookRequest.getPrice())
                .build();
        Book savedBook = bookRepository.save(book);
        bookElasticsearchRepository.save(savedBook);
        log.info("{} database eklendi", savedBook.getTitle());
    }

    @Override
    public void processUpdateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        if (bookRequest.getTitle() != null && !bookRequest.getTitle().trim().isEmpty()) {
            existingBook.setTitle(bookRequest.getTitle());
        }
        if (bookRequest.getAuthor() != null && !bookRequest.getAuthor().trim().isEmpty()) {
            existingBook.setAuthor(bookRequest.getAuthor());
        }
        existingBook.setPrice(bookRequest.getPrice());
        Book updatedBook = bookRepository.save(existingBook);
        bookElasticsearchRepository.save(updatedBook);
        log.info("Güncellendi: {}", updatedBook.getTitle());
    }

    @Override
    public void processDeleteBook(Long id) {
        bookRepository.deleteById(id);
        bookElasticsearchRepository.deleteById(id);
        log.info("Silindi: {}", id);
    }
}
