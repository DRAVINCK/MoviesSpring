package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.MovieRequest;
import com.dravinck.movies.controller.response.MovieResponse;
import com.dravinck.movies.entity.Movie;
import com.dravinck.movies.mapper.MovieMapper;
import com.dravinck.movies.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Endpoints de filmes")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Cadastrar um filme",description = "Cadastra um filme no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Filme cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@Valid @RequestBody MovieRequest request){
         Movie save = movieService.save(MovieMapper.toMovie(request));
            return ResponseEntity.ok(MovieMapper.toMovieResponse(save));
    }

    @Operation(summary = "Buscar um filme",description = "Busca um filme no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Filme encontrado com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @GetMapping
    public  ResponseEntity<List<MovieResponse>> findAllMovie(){
        return ResponseEntity.ok(movieService.findAll()
                .stream()
                .map(MovieMapper::toMovieResponse)//movie -> MovieMapper.toMovieResponse(movie)
                .toList());
    }


    @Operation(summary = "Buscar um filme por id",description = "Busca um filme no banco de dados por id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Filme encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404", description = "Filme não encontrado",content = @Content())
    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id){
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> updateMovieById(@PathVariable Long id, @Valid @RequestBody MovieRequest request){
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
