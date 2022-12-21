package com.practice.comment.web.dto.user;

import com.practice.comment.domain.user.User;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    private String userName;
    private String userId;
    private String userPassword;

    public User toUserEntity() {
        return User.builder()
                .user_name(userName)
                .user_id(userId)
                .user_password(userPassword)
                .build();
    }
}