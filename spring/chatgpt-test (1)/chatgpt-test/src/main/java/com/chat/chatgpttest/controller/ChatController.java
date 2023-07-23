package com.chat.chatgpttest.controller;

import com.chat.chatgpttest.dto.ChatGPTRequest;
import com.chat.chatgpttest.dto.ChatGPTResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@RestController
@RequestMapping("/bot")
public class ChatController {


    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public JSONObject chat(){
        ChatGPTRequest request = new ChatGPTRequest(model);
        ChatGPTResponse ChatGPTResponse= template.postForObject(apiURL, request, ChatGPTResponse.class);

        JSONObject jsonResponse = new JSONObject(ChatGPTResponse);

        JSONArray msg = jsonResponse.getJSONArray("choices");
//        System.out.println(msg.getJSONObject(0));
        JSONArray content = msg.getJSONObject(0).getJSONObject("message").getJSONArray("content");

        System.out.println(content);
        return jsonResponse;
//        return ChatGPTResponse.getChoices().get(0).getMessage().getContent();
//    public String chat(@RequestParam("prompt") String prompt){
//        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
//        ChatGPTResponse ChatGPTResponse= template.postForObject(apiURL, request, ChatGPTResponse.class);
//        return ChatGPTResponse.getChoices().get(0).getMessage().getContent();
    }
}

