package com.jcmvesco.minesweeper.configuration;

import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.info.MapInfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    @SuppressWarnings("unchecked")
    public InfoEndpoint infoEndpoint() {
        Yaml yaml = new Yaml();
        Map<String, Object> appInfo = yaml.loadAs(this.getClass().getClassLoader().getResourceAsStream("app-info.yml"),
                Map.class);
        return new InfoEndpoint(List.of(new MapInfoContributor(appInfo)));
    }
}
