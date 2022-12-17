package com.practice.comment.service.comment;

import com.practice.comment.domain.comment.Comment;
import com.practice.comment.domain.comment.CommentRepository;
import com.practice.comment.web.dto.comment.ReadCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<ReadCommentResponseDto> getCommentListByBoardCode(int boardCode) throws Exception {
        return changeToReadCommentResponseDtoList(commentRepository.findCommentListByBoardCode(boardCode));
    }

    private List<ReadCommentResponseDto> changeToReadCommentResponseDtoList(List<Comment> commentList) {
        return commentList.stream()
                .map(Comment::toReadCommentResponseDto)
                .collect(Collectors.toList());
    }
}