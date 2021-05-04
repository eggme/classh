package me.eggme.classh.security.listener;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.repository.*;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberRolesRepository memberRolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static AtomicInteger count = new AtomicInteger(0);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) return;

        setupSecurityResources();


        alreadySetup = true;
    }

    private void setupSecurityResources() {
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        Role mdRole = createRoleIfNotFound("ROLE_MD", "MD");
        Role userRole = createRoleIfNotFound("ROLE_USER", "일반유저");
        Resources adminResources = createResourceIfNotFound("/admin/**", "", "url");
        Resources mdResources = createResourceIfNotFound("/md/**", "", "url");
        createRoleResourceIfNotFound(adminRole, adminResources);
        createRoleResourceIfNotFound(mdRole, mdResources);
        createAdminIfNotFound(adminRole, "admin@hoflearn.com", "1q2w3e4r");
        createMdIfNotFound(mdRole, "md@hoflearn.com", "1q2w3e4r");
    }

    private void createMdIfNotFound(Role mdRole, String email, String password) {
        MemberRoles temp = new MemberRoles();
        temp.setRole(mdRole);
        int count = memberRepository.countByMemberRoles(mdRole.getRoleName());
        if( count == 0) {
            Member member = Member.builder()
                    .username(email)
                    .password(passwordEncoder.encode(password))
                    .nickName("MD")
                    .build();
            Member savedMember = memberRepository.save(member);
            MemberRoles memberRoles = new MemberRoles();
            memberRoles.setRole(mdRole);
            memberRoles.setMember(savedMember);
            memberRolesRepository.save(memberRoles);
        }
    }

    private void createAdminIfNotFound(Role adminRole, String email, String password) {
        MemberRoles temp = new MemberRoles();
        temp.setRole(adminRole);
        int count = memberRepository.countByMemberRoles(adminRole.getRoleName());
        if( count == 0){
            Member member = Member.builder()
                    .username(email)
                    .password(passwordEncoder.encode(password))
                    .nickName("운영자")
                    .build();
            Member savedMember = memberRepository.save(member);
            MemberRoles memberRoles = new MemberRoles();
            memberRoles.setRole(adminRole);
            memberRoles.setMember(savedMember);
            memberRolesRepository.save(memberRoles);
        }
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
