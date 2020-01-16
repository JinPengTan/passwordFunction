package jwt.repository;

import jwt.domain.ExtendUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ExtendUser entity.
 */
@Repository
public interface ExtendUserRepository extends JpaRepository<ExtendUser, Long> {

    @Query(value = "select distinct extendUser from ExtendUser extendUser left join fetch extendUser.profiles",
        countQuery = "select count(distinct extendUser) from ExtendUser extendUser")
    Page<ExtendUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct extendUser from ExtendUser extendUser left join fetch extendUser.profiles")
    List<ExtendUser> findAllWithEagerRelationships();

    @Query("select extendUser from ExtendUser extendUser left join fetch extendUser.profiles where extendUser.id =:id")
    Optional<ExtendUser> findOneWithEagerRelationships(@Param("id") Long id);

}
