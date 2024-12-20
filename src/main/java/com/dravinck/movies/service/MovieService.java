package com.dravinck.movies.service;

import com.dravinck.movies.entity.Category;
import com.dravinck.movies.entity.Movie;
import com.dravinck.movies.entity.Streaming;
import com.dravinck.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;
    private final MovieRepository movieRepository;

    public List<Movie> findAll(){
        return repository.findAll();
    }

    public Movie save(Movie movie){
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreaming(movie.getStreamings()));
        return repository.save(movie);
    }

    public Optional<Movie> findById(Long id){
        return repository.findById(id);
    }

    public List<Movie> findByCategory(Long category){
        return movieRepository.findMovieByCategories(List.of(Category.builder().id(category).build()));
    }


    public void delete (Long id){
        repository.deleteById(id);
    }

    public Optional<Movie>update(Long id, Movie updateMovie){
        Optional<Movie> optMovie = movieRepository.findById(id);
        if (optMovie.isPresent()){
            Movie movieFound = optMovie.get();
            movieFound.setTitle(updateMovie.getTitle());
            movieFound.setDescription(updateMovie.getDescription());
            movieFound.setReleaseDate(updateMovie.getReleaseDate());
            movieFound.setRating(updateMovie.getRating());

            movieFound.getCategories().clear();
            movieFound.getCategories().addAll(this.findCategories(updateMovie.getCategories()));

            movieFound.getStreamings().clear();
            movieFound.getStreamings().addAll(this.findStreaming(updateMovie.getStreamings()));

            movieRepository.save(movieFound);

            return Optional.of(movieFound);
        }
        return Optional.empty();
    }

    private List<Category> findCategories(List<Category> categories){
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category -> categoryService.findById(category.getId()).ifPresent(categoriesFound::add));
        return categoriesFound;
    }

    private List<Streaming> findStreaming(List<Streaming> streamings){
        List<Streaming> streamingsFound = new ArrayList<>();
        streamings.forEach(streaming -> streamingService.findById(streaming.getId()).ifPresent(streamingsFound::add));
        return streamingsFound;
    }
}
