package com.practice.comment.web.dto.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadCommentResponseDto {
    private int commentCode;
    private int parentCommentCode;
    private int userCode;
    private String userName;
    private String parentUserName;
    private String createDate;
    private String comment;
    private String nonBreakingSpace;
    private boolean replyFlag;
    private boolean haveReplyFlag;
}