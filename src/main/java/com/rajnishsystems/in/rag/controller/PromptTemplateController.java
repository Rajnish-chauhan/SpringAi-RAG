package com.rajnishsystems.in.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/**")
public class PromptTemplateController {

    private final ChatClient chatClient;
    public PromptTemplateController(@Qualifier("chatClient") ChatClient chatclient){
        this.chatClient=chatclient;
    }

/*    String promptTemplate= """
            A customer name {customerName} sent the following message:
            {customerMessage}

            write polite and email response addressing the issue.
            Maintain professional tone and provide reassurance

            Response as if you've write email body only,
            Don't include subject signature
            """;*/

    @Value("classpath:/promptTemplate/userPromptTemplate.st")
    Resource userPromptTemplate;

    @GetMapping("/email")
    public String emailResponse(@RequestParam("customerName") String customerName
            ,@RequestParam("customerMessage") String customerMessage){

        return chatClient
                .prompt()
                .options(OpenAiChatOptions.builder()
                        .model("gpt-4o-mini"))
                .system(("""
                        Your a professional customer service assistant which helps to drafting email
                        and help to improve productivity of customer support team.
                        """))
                .user(promptUserSpec ->
                        promptUserSpec.text(userPromptTemplate)
                                .param("customerName",customerName)
                                .param("customerMessage",customerMessage))
                .call()
                .content();
    }
}
