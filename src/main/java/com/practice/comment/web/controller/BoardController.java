package com.practice.comment.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/{boardCode}")
    public String loadBoardPage() {
        return "/board/board";
    }
}