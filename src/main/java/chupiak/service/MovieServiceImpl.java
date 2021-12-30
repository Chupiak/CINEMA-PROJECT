package chupiak.service;

import chupiak.dao.MovieDao;
import chupiak.model.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Optional<Movie> get(Long id) {
        return movieDao.get(id);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
