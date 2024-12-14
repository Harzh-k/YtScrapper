package com.ytscrapper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class YtConfig {

    @Value("${youtube.api.key}")
    private String youtubeapikey;

    @Value("${youtube.api.url}")
    private String youtubeapiurl;

}
