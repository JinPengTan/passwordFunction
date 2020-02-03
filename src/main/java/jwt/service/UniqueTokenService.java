package jwt.service;

import jwt.domain.UniqueToken;
import jwt.repository.UniqueTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UniqueToken}.
 */
@Service
@Transactional
public class UniqueTokenService {

    private final Logger log = LoggerFactory.getLogger(UniqueTokenService.class);

    private final UniqueTokenRepository uniqueTokenRepository;

    public UniqueTokenService(UniqueTokenRepository uniqueTokenRepository) {
        this.uniqueTokenRepository = uniqueTokenRepository;
    }

    /**
     * Save a uniqueToken.
     *
     * @param uniqueToken the entity to save.
     * @return the persisted entity.
     */
    public UniqueToken save(UniqueToken uniqueToken) {
        log.debug("Request to save UniqueToken : {}", uniqueToken);
        return uniqueTokenRepository.save(uniqueToken);
    }

    /**
     * Get all the uniqueTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UniqueToken> findAll(Pageable pageable) {
        log.debug("Request to get all UniqueTokens");
        return uniqueTokenRepository.findAll(pageable);
    }


    /**
     * Get one uniqueToken by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UniqueToken> findOne(Long id) {
        log.debug("Request to get UniqueToken : {}", id);
        return uniqueTokenRepository.findById(id);
    }

    /**
     * Delete the uniqueToken by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UniqueToken : {}", id);
        uniqueTokenRepository.deleteById(id);
    }
}
