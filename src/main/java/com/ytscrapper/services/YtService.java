package com.ytscrapper.services;

//import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytscrapper.config.YtConfig;

@Service
public class YtService {

    @Autowired
    public YtConfig ytconfig;

    public String extractVideoId(String videolink){
        // Pattern urlpattern1=Pattern.compile("\\/watch\\?v=|youtu\\.be\\/)[a-zA-Z0-9_-]{11}(&.*)?$" ,Pattern.CASE_INSENSITIVE);
        // Matcher matcher1=urlpattern1.matcher(videolink);
        Pattern urlpattern2=Pattern.compile("youtu.be\\/(.{11})",Pattern.CASE_INSENSITIVE);
        Matcher matcher2=urlpattern2.matcher(videolink);


        if(matcher2.find()){
            return matcher2.group(1);
        }//else if(matcher2.find()){
        //     return matcher2.group(1);
        // }

        return null;
    }

    public JsonNode getVideoDetails(String videoId) throws Exception{

        String apikey="key="+ytconfig.getYoutubeapikey();
        String apiurl=ytconfig.getYoutubeapiurl();
        String idpara="id="+videoId;
        String partpara="part=snippet";

        String url=apiurl+"?"+apikey+"&"+partpara+"&"+idpara;

        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url, String.class);

        //System.out.println(response);
        //JSON Parsing 
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readTree(response).path("items").get(0).path("snippet");
    }

}
