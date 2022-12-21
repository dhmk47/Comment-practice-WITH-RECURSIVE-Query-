package com.practice.comment.service.auth;

import com.practice.comment.web.dto.user.CreateUserRequestDto;

import javax.security.auth.login.LoginException;

public interface AuthService {
    public boolean signUpUser(CreateUserRequestDto createUserRequestDto) throws Exception;
}