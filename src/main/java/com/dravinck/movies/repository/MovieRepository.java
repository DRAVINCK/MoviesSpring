package com.dravinck.movies.repository;

import com.dravinck.movies.entity.Category;
import com.dravinck.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMovieByCategories(List<Category> categories);
}
