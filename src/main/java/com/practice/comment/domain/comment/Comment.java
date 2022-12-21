package com.practice.comment.domain.comment;

import com.practice.comment.web.dto.comment.ReadCommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int comment_code;
    private int parent_comment_code;
    private int user_code;
    private int board_code;
    private String user_name;
    private String comment;
    private int parent_user_code;
    private LocalDateTime create_date;
    private int reply_flag;
    private int have_reply_flag;

    public ReadCommentResponseDto toReadCommentResponseDto() {
        return ReadCommentResponseDto.builder()
                .commentCode(comment_code)
                .parentCommentCode(parent_comment_code)
                .userCode(user_code)
                .userName(user_name)
                .comment(comment)
                .createDate(create_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .replyFlag(reply_flag == 1)
                .haveReplyFlag(have_reply_flag != 0)
                .build();
    }
}