package com.dravinck.movies.controller;

import com.dravinck.movies.config.TokenService;
import com.dravinck.movies.controller.request.LoginResquest;
import com.dravinck.movies.controller.request.UserRequest;
import com.dravinck.movies.controller.response.LoginResponse;
import com.dravinck.movies.controller.response.UserResponse;
import com.dravinck.movies.entity.User;
import com.dravinck.movies.exceptions.UsernameOrPasswordinvalidException;
import com.dravinck.movies.mapper.UserMapper;
import com.dravinck.movies.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints de autenticação")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request){
        User savadUser = userService.save(UserMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savadUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginResquest request){
        try {
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticate.getPrincipal();

            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            throw new UsernameOrPasswordinvalidException("Usuário ou senha inválido");
        }
    }
}
