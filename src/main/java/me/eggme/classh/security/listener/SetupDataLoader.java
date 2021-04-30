package me.eggme.classh.security.listener;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.Resources;
import me.eggme.classh.domain.entity.Role;
import me.eggme.classh.domain.entity.RoleResources;
import me.eggme.classh.repository.ResourcesRepository;
import me.eggme.classh.repository.RoleRepository;
import me.eggme.classh.repository.RoleResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourcesRepository resourceRepository;
    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    private static AtomicInteger count = new AtomicInteger(0);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) return;

        setupSecurityResources();


        alreadySetup = true;
    }

    private void setupSecurityResources() {

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        Role userRole = createRoleIfNotFound("ROLE_USER", "일반유저");
        Resources adminResources = createResourceIfNotFound("/admin/**", "", "url");
        createRoleResourceIfNotFound(adminRole, adminResources);

    }

    @Transactional
    public RoleResources createRoleResourceIfNotFound(Role role, Resources resources) {
        RoleResources roleResources = roleResourcesRepository.findByRoleAndResources(role, resources);
        log.info(roleResources != null ? "RoleResources added" : "RoleResources not added");
        if(roleResources == null){
            roleResources = RoleResources.builder()
                            .role(role)
                            .resources(resources)
                            .build();
        }
        return roleResourcesRepository.save(roleResources);
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourcesName, String httpMethod, String resourcesType) {
        Resources resources = resourceRepository.findByResourcesNameAndResourcesType(resourcesName, resourcesType);
        log.info(resources != null ? "Resources added" : "Resources not added");
        if(resources == null){
            resources = Resources.builder()
                    .resourcesName(resourcesName)
                    .httpMethod(httpMethod)
                    .resourcesType(resourcesType)
                    .orderNum(count.incrementAndGet())
                    .build();
        }
        return resourceRepository.save(resources);
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {
        Role role = roleRepository.findByRoleName(roleName);

        if(role == null){
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }
}
