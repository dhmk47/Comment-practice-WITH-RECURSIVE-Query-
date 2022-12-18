package com.practice.comment.service.comment;

import org.springframework.stereotype.Service;

@Service
public class AlertService {

    public void successAlert() {
        System.out.println("성공");
    }

    public void failedAlert() {
        System.out.println("실패");
    }
}