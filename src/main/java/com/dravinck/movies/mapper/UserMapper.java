package com.dravinck.movies.mapper;

import com.dravinck.movies.controller.request.UserRequest;
import com.dravinck.movies.controller.response.UserResponse;
import com.dravinck.movies.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .email(request.password())
                .build();
    }
    
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
