package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SpringBootMvcRestfulApiArticle2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcRestfulApiArticle2Application.class, args);
    }
}
