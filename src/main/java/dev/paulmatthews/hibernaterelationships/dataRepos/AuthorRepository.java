package dev.paulmatthews.hibernaterelationships.dataRepos;

import dev.paulmatthews.hibernaterelationships.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
