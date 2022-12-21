package com.practice.comment.web.controller.api;

import com.google.gson.Gson;
import com.practice.comment.handler.aop.exception.CustomExceptionHandler;
import com.practice.comment.handler.aop.exception.ProductErrorResult;
import com.practice.comment.handler.aop.exception.ProductException;
import com.practice.comment.service.comment.ProductService;
import com.practice.comment.web.dto.CreateProductRequestDto;
import com.practice.comment.web.dto.CreateProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductRestControllerTest {
    @InjectMocks
    private ProductRestController productRestController;
    @Mock
    private ProductService productService;

    private Gson gson;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(productRestController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void mockMvc가Null이아님() throws Exception {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void 제품조회성공() throws Exception {
        String url = "/api/v1/product/세탁기";

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto("김치냉장고");

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .content(gson.toJson(createProductRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 제품등록실패() throws Exception {
        String url = "/api/v1/product";

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto("김치냉장고");

        when(productService.createProduct(createProductRequestDto)).thenThrow(new ProductException(ProductErrorResult.ALREADY_HAS_PRODUCT_EXCEPTION));

        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(createProductRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        resultActions.andDo(print());
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 제품등록성공() throws Exception {
        String url = "/api/v1/product";

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto("김치냉장고");
        CreateProductResponseDto createProductResponseDto = new CreateProductResponseDto(1, "김치냉장고");

        when(productService.createProduct(createProductRequestDto)).thenReturn(createProductResponseDto);

        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(createProductRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 제품조회실패() throws Exception {
        String url = "/api/v1/product/냉장고";

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .characterEncoding("utf-8"));

        resultActions.andExpect(status().isBadRequest());
    }
}