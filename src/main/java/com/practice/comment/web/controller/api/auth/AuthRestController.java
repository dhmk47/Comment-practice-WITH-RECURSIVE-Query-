package com.practice.comment.web.controller.api.auth;

import com.practice.comment.service.auth.AuthService;
import com.practice.comment.service.auth.PrincipalDetails;
import com.practice.comment.web.dto.CustomResponseDto;
import com.practice.comment.web.dto.user.CreateUserRequestDto;
import com.practice.comment.web.dto.user.ReadUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<?> signUpUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        boolean status = false;

        try {
            authService.signUpUser(createUserRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CustomResponseDto<>(-1, "signUp user failed", status));
        }

        return ResponseEntity.ok(new CustomResponseDto<>(1, "signUp user successful", status));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        ReadUserResponseDto user = null;

        if(principalDetails != null) {
            user = principalDetails.getUser().toUserDto();
        }

        return ResponseEntity.ok(new CustomResponseDto<>(1, "load user successful", user));
    }
}