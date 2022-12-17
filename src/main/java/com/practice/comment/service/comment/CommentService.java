package com.practice.comment.service.comment;

import com.practice.comment.web.dto.comment.ReadCommentResponseDto;

import java.util.List;

public interface CommentService {
    public List<ReadCommentResponseDto> getCommentListByBoardCode(int boardCode) throws Exception;
}
