package com.practice.comment.domain.user;

import com.practice.comment.web.dto.user.ReadUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int user_code;
    private String user_name;
    private String user_id;
    private String user_password;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private String roles;

    public ReadUserResponseDto toUserDto() {
        return ReadUserResponseDto.builder()
                .userCode(user_code)
                .userName(user_name)
                .userId(user_id)
                .createDate(create_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updateDate(update_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    public List<String> getUserRoleList() {
        if(roles.isBlank()) {
            return Collections.EMPTY_LIST;
        }

        return Arrays.asList(roles.replaceAll(" ", "").split(","));
    }
}