package com.dravinck.movies.repository;

import com.dravinck.movies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
