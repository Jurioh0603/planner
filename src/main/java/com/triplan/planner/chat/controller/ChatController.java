package com.triplan.planner.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/chat/chatList")
    public String chatList(){
        return "/chat/chatList";
    }
}
