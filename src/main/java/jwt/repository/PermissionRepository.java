package jwt.repository;

import jwt.domain.Permission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Permission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT C FROM Permission C")
    List<Permission> findAllPermission();


}
