package com.example.bookservice.repository.elasticsearch;

import com.example.bookservice.entities.Book;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookElasticsearchRepository extends ElasticsearchRepository<Book,Long> {
    @Query("{\"match\": {\"title\": \"?0\"}}")
    List<Book> findByTitle(String title);

    @Query("{\"match\": {\"author\": \"?0\"}}")
    List<Book> findByAuthor(String author);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"author\": \"?1\"}}]}}")
    List<Book> findByTitleAndAuthor(String title, String author);
}
