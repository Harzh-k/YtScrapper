package com.ytscrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytscrapper.services.YtService;

@Controller
public class YtController {

    @Autowired
    private YtService youtubeService;

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @PostMapping("/detail")
    public String getData(@RequestParam String videolink,Model model){
        String videoId=youtubeService.extractVideoId(videolink);
        //System.out.println(videoId);

        if(videoId!=null){
            try{
                JsonNode videoDetails=youtubeService.getVideoDetails(videoId);
                 String title=videoDetails.path("title").asText();
                 String description=videoDetails.path("description").asText();
                 String thumburl=videoDetails.path("thumbnails").path("standard").path("url").asText();
                 String tags[] =new ObjectMapper().treeToValue(videoDetails.path("tags"), String[].class);

                 model.addAttribute("vtitle", title);
                 model.addAttribute("vdescription", description);
                 model.addAttribute("vthumburl", thumburl);
                 model.addAttribute("vtags", tags);

                 //System.out.println(model);

                 return "detail";


            }catch(Exception e){
                e.printStackTrace();
                return "index";
            }
        }
        return "error";
    }

}
