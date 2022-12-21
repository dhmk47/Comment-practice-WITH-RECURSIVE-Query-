package com.practice.comment.domain.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
    public int saveUser(User user) throws Exception;
    public User findUserByUserId(String user_id) throws Exception;
}