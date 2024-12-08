package com.dravinck.movies.controller;


import com.dravinck.movies.controller.request.MovieRequest;
import com.dravinck.movies.entity.Movie;
import com.dravinck.movies.mapper.MovieMapper;
import com.dravinck.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public void save(@RequestBody MovieRequest request){
        Movie savedMovie = movieService.save(MovieMapper.toMovie(request));
    }







}
