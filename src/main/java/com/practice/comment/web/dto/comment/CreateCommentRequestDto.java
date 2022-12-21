package com.practice.comment.web.dto.comment;

import com.practice.comment.domain.comment.Comment;
import lombok.Data;

@Data
public class CreateCommentRequestDto {
    private int userCode;
    private int boardCode;
    private String comment;
    private int parentCode;
    private int parentUserCode;

    public Comment toCommentEntity() {
        return Comment.builder()
                .user_code(userCode)
                .board_code(boardCode)
                .comment(comment)
                .parent_comment_code(parentCode)
                .parent_user_code(parentUserCode)
                .build();
    }
}