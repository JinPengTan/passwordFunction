package jwt.repository;

import jwt.domain.TokenHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TokenHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenHistoryRepository extends JpaRepository<TokenHistory, Long> {

}
