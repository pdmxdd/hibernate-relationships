package dev.paulmatthews.hibernaterelationships.dataRepos;

import dev.paulmatthews.hibernaterelationships.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
