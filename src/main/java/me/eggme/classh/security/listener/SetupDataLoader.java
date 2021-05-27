package me.eggme.classh.security.listener;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseState;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.repository.*;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
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
    /* 임시 */
    @Autowired InstructorRepository instructorRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired SignUpCourseRepository signUpCourseRepository;
    @Autowired CourseService courseService;
    @PersistenceContext EntityManager em;

    private static AtomicInteger count = new AtomicInteger(0);


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) return;

        setupSecurityResources();

        alreadySetup = true;
        /* 테스트용 */
        PaymentAndCartTest();
    }

    @Transactional
    public void PaymentAndCartTest(){
        /* 장바구니 , 결제 테스트용  */
        Member savedMember = memberRepository.findByUsername("admin@hoflearn.com").orElseThrow(() ->
                new UsernameNotFoundException("as"));

        Course course = new Course("asdasd"); // 강의 엔티티를 만듬
        course.setCourseState(CourseState.RELEASE); // 엔티티 속성을 변경함
        Course savedCourse = courseRepository.save(course); // 엔티티를 영속상태로 만듬
        savedCourse.setUrl("temp_" + savedCourse.getId()); // 영속상태의 엔티티를 변경함 (변경감지)
        savedCourse.setPrice(56000);
        // 작동안됨 -> 해결 해당 메서드를 호출하는 상위 컴포넌트에서 @Transactional 어노테이션을
        // 붙이니까 됐는데 왜 그런지는 모르겠군
        courseService.createDefaultSessionAndClass(savedCourse);

        Instructor instructor = new Instructor();
        Instructor savedInstructor = instructorRepository.save(instructor);
        savedInstructor.setMember(savedMember);
        savedInstructor.setCourses(savedCourse);

        savedMember.setInstructor(savedInstructor);
        savedCourse.setInstructor(savedInstructor);

        SignUpCourse signUpCourse = new SignUpCourse();

        savedMember.connectCourse(savedCourse, signUpCourse);
        signUpCourseRepository.save(signUpCourse);
    }

    @Transactional
    public void setupSecurityResources() {
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

    @Transactional
    public void createMdIfNotFound(Role mdRole, String email, String password) {
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

    @Transactional
    public void createAdminIfNotFound(Role adminRole, String email, String password) {
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
