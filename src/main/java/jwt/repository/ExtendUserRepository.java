package jwt.repository;

import jwt.domain.ExtendUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the ExtendUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendUserRepository extends JpaRepository<ExtendUser, Long> {

    @Query("SELECT c FROM ExtendUser c WHERE c.profile.id = :id")
    Set<ExtendUser> findALlByProfileId(@Param(value = "id") long id);

    @Query("SELECT c FROM ExtendUser c")
    List<ExtendUser> findAllExtendUsers();

}
