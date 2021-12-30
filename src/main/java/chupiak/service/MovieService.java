package chupiak.service;

import chupiak.model.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie add(Movie movie);

    Optional<Movie> get(Long id);

    List<Movie> getAll();
}
