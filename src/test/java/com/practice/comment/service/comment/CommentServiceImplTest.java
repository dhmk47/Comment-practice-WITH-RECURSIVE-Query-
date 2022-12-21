package com.practice.comment.service.comment;

import com.practice.comment.domain.comment.Comment;
import com.practice.comment.domain.comment.CommentRepository;
import com.practice.comment.web.dto.comment.CreateCommentRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.MyBatisSystemException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    private CommentService commentService;

    @BeforeEach()
    void init() {
        commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    void 댓글작성실패_Repository에러() throws Exception {
        // given
        CreateCommentRequestDto commentDto = new CreateCommentRequestDto();
        Comment commentEntity = commentDto.toCommentEntity();
        when(commentRepository.saveComment(commentEntity)).thenThrow(MyBatisSystemException.class);


        // when
        assertThatExceptionOfType(MyBatisSystemException.class)
                .isThrownBy(() -> {
                    commentService.saveComment(commentDto);
                });

        // then
    }

    @Test
    void 댓글작성성공() throws Exception {
        // given
        CreateCommentRequestDto commentDto = new CreateCommentRequestDto();
        Comment commentEntity = commentDto.toCommentEntity();
        when(commentRepository.saveComment(commentEntity)).thenReturn(1);

        // when
        boolean result = commentService.saveComment(commentDto);

        // then
        assertThat(result).isTrue();
    }
}