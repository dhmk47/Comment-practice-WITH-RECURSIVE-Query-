package com.practice.comment.domain.comment;

import com.practice.comment.web.dto.comment.ReadCommentResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Comment {
    private int comment_code;
    private int parent_comment_code;
    private int user_code;
    private String user_name;
    private String comment;
    private LocalDateTime create_date;
    private int reply_flag;

    public ReadCommentResponseDto toReadCommentResponseDto() {
        return ReadCommentResponseDto.builder()
                .commentCode(comment_code)
                .parentCommentCode(parent_comment_code)
                .userCode(user_code)
                .userName(user_name)
                .comment(comment)
                .createDate(create_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .replyFlag(reply_flag == 1)
                .build();
    }
}