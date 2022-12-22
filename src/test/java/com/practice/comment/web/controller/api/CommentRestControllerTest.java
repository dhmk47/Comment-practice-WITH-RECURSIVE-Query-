package com.practice.comment.web.controller.api;

import com.google.gson.Gson;
import com.practice.comment.service.comment.CommentService;
import com.practice.comment.web.dto.comment.CreateCommentRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentRestControllerTest {

    @Mock
    private CommentService commentService;
    @InjectMocks
    private CommentRestController commentRestController;
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(commentRestController)
                .build();
    }

    @Test
    public void gson_mockMvc_Null아님() {
        assertThat(gson).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void code200() throws Exception {
        // given
        CreateCommentRequestDto commentDto = new CreateCommentRequestDto();
        String url = "/api/v1/comment";
        // when
        ResultActions result =  mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}