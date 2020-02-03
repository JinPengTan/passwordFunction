package jwt.service;

import jwt.domain.Token;
import jwt.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Token}.
 */
@Service
@Transactional
public class TokenService {

    private final Logger log = LoggerFactory.getLogger(TokenService.class);

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * Save a token.
     *
     * @param token the entity to save.
     * @return the persisted entity.
     */
    public Token save(Token token) {
        log.debug("Request to save Token : {}", token);
        return tokenRepository.save(token);
    }

    /**
     * Get all the tokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Token> findAll(Pageable pageable) {
        log.debug("Request to get all Tokens");
        return tokenRepository.findAll(pageable);
    }


    /**
     * Get one token by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Token> findOne(Long id) {
        log.debug("Request to get Token : {}", id);
        return tokenRepository.findById(id);
    }

    /**
     * Delete the token by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Token : {}", id);
        tokenRepository.deleteById(id);
    }
}
