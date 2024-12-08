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

@RestController
@RequestMapping("movies/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> save(@RequestBody MovieRequest request){
         Movie save = movieService.save(MovieMapper.toMovie(request));
            return ResponseEntity.ok(MovieMapper.toMovieResponse(save));
    }

    @GetMapping
    public  ResponseEntity<List<MovieResponse>> findAll(){
        return ResponseEntity.ok(movieService.findAll()
                .stream()
                .map(MovieMapper::toMovieResponse)//movie -> MovieMapper.toMovieResponse(movie)
                .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id){
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
