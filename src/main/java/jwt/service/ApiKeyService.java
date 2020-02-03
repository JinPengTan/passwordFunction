package jwt.service;

import jwt.domain.ApiKey;
import jwt.repository.ApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiKey}.
 */
@Service
@Transactional
public class ApiKeyService {

    private final Logger log = LoggerFactory.getLogger(ApiKeyService.class);

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    /**
     * Save a apiKey.
     *
     * @param apiKey the entity to save.
     * @return the persisted entity.
     */
    public ApiKey save(ApiKey apiKey) {
        log.debug("Request to save ApiKey : {}", apiKey);
        return apiKeyRepository.save(apiKey);
    }

    /**
     * Get all the apiKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApiKey> findAll(Pageable pageable) {
        log.debug("Request to get all ApiKeys");
        return apiKeyRepository.findAll(pageable);
    }


    /**
     * Get one apiKey by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApiKey> findOne(Long id) {
        log.debug("Request to get ApiKey : {}", id);
        return apiKeyRepository.findById(id);
    }

    /**
     * Delete the apiKey by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiKey : {}", id);
        apiKeyRepository.deleteById(id);
    }
}
