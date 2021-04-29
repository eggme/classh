package me.eggme.classh.security.service;

import me.eggme.classh.domain.entity.Resources;
import me.eggme.classh.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    private ResourceRepository resourceRepository;

    public SecurityResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList(){

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourceRepository.findAllResources();
        resourcesList.forEach(resources -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            resources.getRoleResourcesSet().forEach(roleResource -> {
                configAttributeList.add(new SecurityConfig(roleResource.getRole().getRoleName()));
            });
        });
        return result;
    }
}
