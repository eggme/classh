package me.eggme.classh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:message.properties")
public class ClasshApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClasshApplication.class, args);
    }

}
