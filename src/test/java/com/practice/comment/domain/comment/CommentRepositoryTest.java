package com.practice.comment.domain.comment;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 댓글작성실패_존재하지않는_필드명이있음() throws Exception {
        // given
        Comment comment = Comment.builder()
                .comment("댓글테스트")
                .build();

        // when
        assertThatExceptionOfType(MyBatisSystemException.class)
                .isThrownBy(() -> {
                    commentRepository.saveComment(comment);
                });

        // then
    }

    @Test
    void 댓글작성성공() throws Exception {
        // given
        Comment comment = Comment.builder()
                .comment("댓글테스트")
                .build();

        // when
        int result = commentRepository.saveComment(comment);

        // then
        assertThat(result).isEqualTo(1);
    }
}