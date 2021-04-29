package me.eggme.classh.security.listener;

import me.eggme.classh.domain.entity.Resources;
import me.eggme.classh.domain.entity.Role;
import me.eggme.classh.domain.entity.RoleResources;
import me.eggme.classh.repository.ResourceRepository;
import me.eggme.classh.repository.RoleRepository;
import me.eggme.classh.repository.RoleResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceRepository resourceRepository;
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
        Resources adminResouces = createResourceIfNotFound("/admin/**", "", "url");
        createRoleResourceIfNotFound(adminRole, adminResouces);

    }

    @Transactional
    public RoleResources createRoleResourceIfNotFound(Role role, Resources resources) {
        RoleResources roleResources = roleResourcesRepository.findByRoleAndResources(role, resources);

        if(roleResources == null){
            roleResources = RoleResources.builder()
                            .role(role)
                            .resources(resources)
                            .build();
        }
        return roleResourcesRepository.save(roleResources);
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, String resourceType) {
        Resources resources = resourceRepository.findByResourcNameAndHttpMethod(resourceName, httpMethod);

        if(resources == null){
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
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
