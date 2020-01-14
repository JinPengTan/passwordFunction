package jwt.repository;

import jwt.domain.Cycle;
import jwt.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Cycle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {

    @Query("select cycle from Cycle cycle where cycle.user.login = ?#{principal.username}")
    List<Cycle> findByUserIsCurrentUser();

    @Query("SELECT c FROM Cycle c  WHERE c.user.login = :login")
    List<Cycle> findAllByUserLogin(@Param("login") String login);

    int countByUser(User user);

    @Query("SELECT  c FROM Cycle  c WHERE c.user.id = :id")
    List<Cycle> findAllById(@Param("id") Long id);
}
