package com.rajnishsystems.in.rag.controller;

import com.rajnishsystems.in.rag.model.CountryCitiesModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/**")
public class StructureOutputController {

    private final ChatClient chatClient;


    public StructureOutputController(ChatClient.Builder builder){
        this.chatClient=builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCitiesModel> chatBeam(@RequestParam("message")String message){
        CountryCitiesModel countryCities=chatClient.prompt()
                .user(message)
                .call()
                .entity(CountryCitiesModel.class);
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("message")String message){
        List<String>countryCities=chatClient.prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list-bean")
    public ResponseEntity<List<CountryCitiesModel>> chatListCountry(@RequestParam("message")String message){
        List<CountryCitiesModel>countryCities=chatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCitiesModel>>() {
                });
        return ResponseEntity.ok(countryCities);
    }

}
