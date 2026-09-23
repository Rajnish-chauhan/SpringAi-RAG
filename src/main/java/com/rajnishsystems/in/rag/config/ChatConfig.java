package com.rajnishsystems.in.rag.config;

import com.rajnishsystems.in.rag.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatConfig {

    @Bean("chatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        OpenAiChatOptions.Builder openAiChatOptions=OpenAiChatOptions.builder().model("gpt-4o-mini").temperature(0.7);
        return chatClientBuilder
                .defaultOptions(openAiChatOptions)
                .defaultAdvisors(List.of(new TokenUsageAuditAdvisor(),new SimpleLoggerAdvisor()))
               .defaultSystem("""
                        You are an internal HR assistant. Your role is to help\s
                        employees with questions related to HR policies, such as\s
                        leave policies, working hours, benefits, and code of conduct.
                        If a user asks for help with anything outside of these topics,\s
                        kindly inform them that you can only assist with queries related to\s
                        HR policies.
                        """)
                .defaultUser("How can you help me ?")
                .build();
    }
}
