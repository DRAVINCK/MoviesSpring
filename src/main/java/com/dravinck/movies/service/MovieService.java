package com.dravinck.movies.service;

import com.dravinck.movies.entity.Movie;
import com.dravinck.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public List<Movie> findAll(){
        return repository.findAll();
    }

    public Movie save(Movie movie){
        return repository.save(movie);
    }

    public Optional<Movie> findById(Long id){
        return repository.findById(id);
    }

    public void delete (Long id){
        repository.deleteById(id);
    }
}
