package com.practice.comment.service.auth;

import com.practice.comment.domain.user.User;
import com.practice.comment.domain.user.UserRepository;
import com.practice.comment.web.dto.user.CreateUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean signUpUser(CreateUserRequestDto createUserRequestDto) throws Exception {
        User user = createUserRequestDto.toUserEntity();
        user.setUser_password(bCryptPasswordEncoder.encode(user.getUser_password()));

        return userRepository.saveUser(user) > 0;
    }
}