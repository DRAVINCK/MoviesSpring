package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.UserRequest;
import com.dravinck.movies.controller.response.UserResponse;
import com.dravinck.movies.entity.User;
import com.dravinck.movies.mapper.UserMapper;
import com.dravinck.movies.repository.UserRepository;
import com.dravinck.movies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request){
        User savadUser = userService.save(UserMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savadUser));
    }
}
