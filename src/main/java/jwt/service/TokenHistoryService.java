package jwt.service;

import jwt.domain.TokenHistory;
import jwt.repository.TokenHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TokenHistory}.
 */
@Service
@Transactional
public class TokenHistoryService {

    private final Logger log = LoggerFactory.getLogger(TokenHistoryService.class);

    private final TokenHistoryRepository tokenHistoryRepository;

    public TokenHistoryService(TokenHistoryRepository tokenHistoryRepository) {
        this.tokenHistoryRepository = tokenHistoryRepository;
    }

    /**
     * Save a tokenHistory.
     *
     * @param tokenHistory the entity to save.
     * @return the persisted entity.
     */
    public TokenHistory save(TokenHistory tokenHistory) {
        log.debug("Request to save TokenHistory : {}", tokenHistory);
        return tokenHistoryRepository.save(tokenHistory);
    }

    /**
     * Get all the tokenHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TokenHistory> findAll(Pageable pageable) {
        log.debug("Request to get all TokenHistories");
        return tokenHistoryRepository.findAll(pageable);
    }


    /**
     * Get one tokenHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TokenHistory> findOne(Long id) {
        log.debug("Request to get TokenHistory : {}", id);
        return tokenHistoryRepository.findById(id);
    }

    /**
     * Delete the tokenHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TokenHistory : {}", id);
        tokenHistoryRepository.deleteById(id);
    }
}
