package com.practice.comment.domain.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentRepository {
    public List<Comment> findCommentListByBoardCode(int board_code) throws Exception;
}