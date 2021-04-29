package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {

    @Query("select r from Resources r, RoleResources rr where r.id=rr.id and r.resourceType = 'url' order by r.orderNum desc")
    List<Resources> findAllResources();

    Resources findByResourcNameAndHttpMethod(String resourceName, String httpMethod);
}
