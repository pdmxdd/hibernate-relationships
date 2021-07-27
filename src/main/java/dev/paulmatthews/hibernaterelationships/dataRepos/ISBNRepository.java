package dev.paulmatthews.hibernaterelationships.dataRepos;

import dev.paulmatthews.hibernaterelationships.models.ISBN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISBNRepository extends CrudRepository<ISBN, Integer> {
}
