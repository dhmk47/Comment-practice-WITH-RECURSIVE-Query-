package com.practice.comment.service.auth;

import com.practice.comment.domain.user.User;
import com.practice.comment.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        try {
            user = userRepository.findUserByUserId(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new PrincipalDetails(user);
    }
}