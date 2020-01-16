package jwt.service;

import jwt.domain.ExtendUser;
import jwt.repository.ExtendUserRepository;
import jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExtendUser}.
 */
@Service
@Transactional
public class ExtendUserService {

    private final Logger log = LoggerFactory.getLogger(ExtendUserService.class);

    private final ExtendUserRepository extendUserRepository;

    private final UserRepository userRepository;

    public ExtendUserService(ExtendUserRepository extendUserRepository, UserRepository userRepository) {
        this.extendUserRepository = extendUserRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a extendUser.
     *
     * @param extendUser the entity to save.
     * @return the persisted entity.
     */
    public ExtendUser save(ExtendUser extendUser) {
        log.debug("Request to save ExtendUser : {}", extendUser);
        long userId = extendUser.getUser().getId();
        userRepository.findById(userId).ifPresent(extendUser::user);
        return extendUserRepository.save(extendUser);
    }

    /**
     * Get all the extendUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtendUser> findAll(Pageable pageable) {
        log.debug("Request to get all ExtendUsers");
        return extendUserRepository.findAll(pageable);
    }

    /**
     * Get all the extendUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ExtendUser> findAllWithEagerRelationships(Pageable pageable) {
        return extendUserRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one extendUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExtendUser> findOne(Long id) {
        log.debug("Request to get ExtendUser : {}", id);
        return extendUserRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the extendUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExtendUser : {}", id);
        extendUserRepository.deleteById(id);
    }
}
