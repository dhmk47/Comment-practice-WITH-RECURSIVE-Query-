package com.practice.comment.web.controller.api;

import com.practice.comment.handler.aop.annotaion.Log;
import com.practice.comment.service.comment.CommentService;
import com.practice.comment.web.dto.CustomResponseDto;
import com.practice.comment.web.dto.comment.ReadCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentRestController {

    private final CommentService commentService;

    @Log
    @GetMapping("/list/{boardCode}")
    public ResponseEntity<?> getCommentListByBoardCode(@PathVariable int boardCode) {
        List<ReadCommentResponseDto> commentList = null;

        try {
            commentList = commentService.getCommentListByBoardCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CustomResponseDto<>(-1, "Failed to load comment list", commentList));
        }

        return ResponseEntity.ok(new CustomResponseDto<>(1, "Successfully loaded the comment list", commentList));
    }
}