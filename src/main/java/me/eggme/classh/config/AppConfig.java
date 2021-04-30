package me.eggme.classh.config;

import me.eggme.classh.repository.ResourcesRepository;
import me.eggme.classh.security.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourceRepository){
        SecurityResourceService securityResourceService = new SecurityResourceService(resourceRepository);
        return securityResourceService;
    }

}
