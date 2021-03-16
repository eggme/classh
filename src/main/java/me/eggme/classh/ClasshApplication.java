package me.eggme.classh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource(value = "classpath:/message.properties", encoding = "UTF-8")
public class ClasshApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClasshApplication.class, args);
    }

}
