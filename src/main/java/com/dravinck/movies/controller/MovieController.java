package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.MovieRequest;
import com.dravinck.movies.controller.response.MovieResponse;
import com.dravinck.movies.entity.Movie;
import com.dravinck.movies.mapper.MovieMapper;
import com.dravinck.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@RequestBody MovieRequest request){
         Movie save = movieService.save(MovieMapper.toMovie(request));
            return ResponseEntity.ok(MovieMapper.toMovieResponse(save));
    }

    @GetMapping
    public  ResponseEntity<List<MovieResponse>> findAllMovie(){
        return ResponseEntity.ok(movieService.findAll()
                .stream()
                .map(MovieMapper::toMovieResponse)//movie -> MovieMapper.toMovieResponse(movie)
                .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> updateMovieById(@PathVariable Long id){
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> updateMovieById(@PathVariable Long id, @RequestBody MovieRequest request){
        return movieService.update(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam Long category){
        return ResponseEntity.ok(movieService.findByCategory(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByMovieId(@PathVariable Long id){
        Optional<Movie> optMovie = movieService.findById(id);
        if (optMovie.isPresent()){
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
