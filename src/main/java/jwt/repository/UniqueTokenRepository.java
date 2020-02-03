package jwt.repository;

import jwt.domain.UniqueToken;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UniqueToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniqueTokenRepository extends JpaRepository<UniqueToken, Long> {

}
