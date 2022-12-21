package com.practice.comment.web.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadUserResponseDto {
    private int userCode;
    private String userName;
    private String userId;
    private String createDate;
    private String updateDate;
}