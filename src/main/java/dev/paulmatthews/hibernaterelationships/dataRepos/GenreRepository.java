package dev.paulmatthews.hibernaterelationships.dataRepos;

import dev.paulmatthews.hibernaterelationships.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
