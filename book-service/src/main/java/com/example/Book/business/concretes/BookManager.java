package com.example.Book.business.concretes;
import com.example.Book.business.abstracts.BookService;
import com.example.Book.dto.BookRequest;
import com.example.Book.entities.Book;
import com.example.Book.repository.elasticsearch.BookElasticsearchRepository;
import com.example.Book.repository.jpa.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class BookManager implements BookService {

    private final  BookRepository bookRepository;
    private final BookElasticsearchRepository bookElasticsearchRepository;

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
    public Book addBook(BookRequest bookRequest) {
        Book book= Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor()).build();

        Book  savedBook= bookRepository.save(book);
        bookElasticsearchRepository.save(savedBook);
        log.info("{} database  eklendi ",savedBook.getTitle());
        return savedBook;
    }

    @Override
    public Book updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        if (bookRequest.getTitle() != null && !bookRequest.getTitle().trim().isEmpty()) {
            existingBook.setTitle(bookRequest.getTitle());
        }
        if (bookRequest.getAuthor() != null && !bookRequest.getAuthor().trim().isEmpty()) {
            existingBook.setAuthor(bookRequest.getAuthor());
        }

        Book updatedBook= bookRepository.save(existingBook);
        bookElasticsearchRepository.save(updatedBook);
        log.info(" Güncellendi ");
       return updatedBook;
    }

    @Override
    public void deleteBook(Long id) {
       bookRepository.deleteById(id);
       bookElasticsearchRepository.deleteById(id);
    }



}
