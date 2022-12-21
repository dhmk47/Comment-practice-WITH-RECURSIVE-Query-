package com.practice.comment.service.comment;

import com.practice.comment.web.dto.comment.CreateCommentRequestDto;
import com.practice.comment.web.dto.comment.ReadCommentResponseDto;

import java.util.List;

public interface CommentService {
    public boolean saveComment(CreateCommentRequestDto createCommentRequestDto) throws Exception;
    public List<ReadCommentResponseDto> getCommentListByBoardCode(int boardCode) throws Exception;
}
