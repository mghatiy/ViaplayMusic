package com.webmusic.springboot;
 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webmusic.springboot.service.MusicDetail;
import org.springframework.web.client.RestTemplate;
 

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8081/SpringBootRestApi/api";
     
    /* GET */
    private static void getUser(){
    }
     
    public static void main(String args[]){
        getUser();
    }
}
