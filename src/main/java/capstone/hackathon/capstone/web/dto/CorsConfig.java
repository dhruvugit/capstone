package capstone.hackathon.capstone.web.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // Replace with your frontend's URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Add the HTTP methods you want to support
                        .allowedHeaders("*"); // You can also specify allowed headers or leave it as "*" to allow all headers

            }

        };

    }

}

