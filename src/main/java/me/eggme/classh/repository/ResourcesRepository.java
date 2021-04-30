package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    @Query("select r from Resources r where r.resourcesType = 'url' order by r.orderNum desc")
    List<Resources> findAllResources();

    @Query("select r from Resources r where r.resourcesName=?1 and r.resourcesType=?2")
    Resources findByResourcesNameAndResourcesType(String resourcesName, String resourcesType);
}
