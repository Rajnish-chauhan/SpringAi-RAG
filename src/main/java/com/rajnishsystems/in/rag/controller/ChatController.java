package com.rajnishsystems.in.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/**")
public class ChatController {

    private final ChatClient chatClient;
    public ChatController(@Qualifier("chatClient") ChatClient chatclient){
        this.chatClient=chatclient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message){
        ChatResponse response = chatClient.prompt("Say hello")
                .call()
                .chatResponse();
        String actualModelUsed = response.getMetadata().getModel();
        System.out.println("Actual model used by API: " + actualModelUsed);
        return chatClient
                .prompt(message)
//                .advisors(new TokenUsageAuditAdvisor())
//                .system("Your Are  an expert of ai tools")
                .call()
                .content();
    }
}
